package ast.statement;

import ast.IdNode;
import ast.Node;
import ast.function.AdecNode;
import ast.function.DecpNode;
import ast.function.FunctionNode;
import ast.function.StatementNode;
import ast.typeNode.AssetTypeNode;
import ast.typeNode.IntTypeNode;
import utils.*;
import utils.StEntry.STEntryAsset;
import utils.StEntry.STEntryFun;
import utils.StEntry.STentry;

import java.util.ArrayList;

public class CallNode implements Node {
    private IdNode id;
    private ArrayList<Node> exp;
    private ArrayList<IdNode> listId;
    private STEntryFun stEntry;

    private int nestingLevel;

    public String getId(){
        return id.getId();
    }

    public CallNode(IdNode id, ArrayList<Node> exp, ArrayList<IdNode> listId) {
        this.id = id;
        this.exp = exp; //actualDecParams
        this.listId = (listId.size()>0?listId:new ArrayList<>()); //actualAdecParams
        this.stEntry = null;
    }



    public ArrayList<IdNode> getListId() {
        return listId;
    }

    @Override
    public String toPrint(String indent) {
        String str = indent + "Call\n" + id.toPrint(indent);
        if(exp != null){
            for (Node expnode : this.exp) {
                str+=expnode.toPrint(indent);
            }
        }
        if(listId!= null) {
            for (Node idnode : this.listId) {
                str += idnode.toPrint(indent);
            }
        }
        return str;
    }

    @Override
    public Node typeCheck() {
        if (stEntry != null &&(stEntry.getNode() instanceof FunctionNode)) {
            DecpNode formalDecParams = stEntry.getNode().getDecpNode();
            int lengthFormalDecPar = formalDecParams == null ? 0 : formalDecParams.getDecp().getListId().size();
            if (exp.size() != lengthFormalDecPar) {
                System.out.println("Incorrect Number of Params in Function " + id.getId());
                System.exit(0);
            }
            AdecNode formalAdecParams = stEntry.getNode().getADec();
            int lengthFormalAdecPar = formalAdecParams == null ? 0 : formalAdecParams.getId().size();
            if (listId.size() !=  lengthFormalAdecPar)
                System.out.println("Incorrect Number of Asset in Function " + id.getId());
            //Check for each params if type is equal with type in ST
            if(formalDecParams != null && exp!= null) {
                for (int i = 0; i < lengthFormalDecPar; i++) {
                    Node actualParam = exp.get(i);
                    if (!Utilities.isSubtype(actualParam.typeCheck(), formalDecParams.getDecp().getListType().get(i).typeCheck())) {
                        System.out.println("Incompatible Parameter for Function " + id.getId());
                        System.exit(0);
                    }
                }
            }
            //Check if all bexp are Int or Asset
            if(formalAdecParams != null) {
                for (int i = 0; i <lengthFormalAdecPar; i++) {
                    if (!Utilities.isSubtype(listId.get(i).typeCheck(), new AssetTypeNode()) &&
                            !Utilities.isSubtype(listId.get(i).typeCheck(), new IntTypeNode())) {
                        System.out.println("Incompatible Asset Parameter for Function " + id.getId());
                        System.exit(0);
                    }
                }
            }
        }
        return stEntry.getType().typeCheck();
    }


    @Override
    public String codGeneration() {
        String callCode = "push $fp \n";
        //I add the assets in inverse order
       /* for(int i = listId.size(); i > 0 ; i--)
            callCode += listId.get(i - 1).accessCodGeneration() + listId.get(i - 1).emptyValueCodGeneration()
                    + "push $a0 \n";*/
        for(int i=0; i < listId.size(); i++)
            callCode += listId.get(i).accessCodGeneration() + listId.get(i).emptyValueCodGeneration()
                    + "push $a0 \n";
        //I add the parameters in inverse order
        for(int i = exp.size(); i > 0 ; i--)
            callCode += exp.get(i - 1).codGeneration()
                    + "push $a0 \n";
                //Now i will set the access link
        callCode += "mv $fp $al \n";
        for(int i = 0; i < nestingLevel - stEntry.getNestingLevel(); i++)
            callCode += "lw $al 0($al) \n";
        callCode += "push $al\n";
        String label = stEntry.getNode().getFunLabel();
        callCode += "jal " + label + "\n"; //jump at label and store the next instruction in ra
        return callCode + "\n";
   }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        if(e.isDeclared(id.getId())== EnvError.NO_DECLARE){
            res.add(new SemanticError(id.getId()+": function is not declared [Call]"));
        }else{
            STentry entry = Environment.lookup(e,id.getId());
            if(entry instanceof STEntryFun){stEntry = (STEntryFun) entry;}
            nestingLevel = e.getNestingLevel();
        }
        if(listId!= null) {
            for (IdNode id : listId) {
                if (e.isDeclared(id.getId()) == EnvError.NO_DECLARE)
                    res.add(new SemanticError(id.getId() + " : assetID no declared [Call]"));
                else
                    res.addAll(id.checkSemantics(e));
            }
        }
        if(exp != null){
            for (Node exp:exp) {
                res.addAll(exp.checkSemantics(e));
            }
        }

        return res;
    }

    @Override
    public Environment checkEffects(Environment e) {
        STentry stentry = Environment.lookup(e,id.getId());

        if(stentry instanceof STEntryFun){
            STEntryFun st = (STEntryFun) stentry;
            ArrayList<StatementNode> stmList= ((STEntryFun) st).getNode().getStatement();

            e = Environment.newScope(e);

            //Identificatori Asset

            //Sappiamo per definizione che size dei parametri formali == size parametri attuali
            ArrayList<IdNode> actualParameter = listId != null ? listId : new ArrayList<>();
            ArrayList<IdNode> formalParameter = st.getNode().getADec() != null ? st.getNode().getADec().getId() : new ArrayList<>();

            for (int i = 0; i < actualParameter.size(); i++) {
                //aggiorno l'attuale in base al formale
                STentry entryA = Environment.lookup(e, actualParameter.get(i).getId());
                //STentry entryF = Environment.lookup(e,formalParameter.get(i).getId());
                Environment.addDeclaration(e, formalParameter.get(i).getId(), ((STEntryAsset)entryA).getLiquidity());
                if(((STEntryAsset)entryA).getLiquidity()> 0)
                    ((STEntryAsset)entryA).setLiquidity(0); //gli asset passati per parametro vengono azzerati
            }

            if(stmList != null) {
                for(StatementNode stm : stmList){
                    if((stm.getStatement() instanceof CallNode && this.id == ((CallNode) stm.getStatement()).id) ) {
                        return LiquidityUtils.fixPointMethod(e, st.getNode(), this);
                    }
                }
            }
            if(stmList != null){
                for(StatementNode stm : stmList)
                      e = stm.checkEffects(e);//Avvio l'analisi degli effetti su gli statement
            }

            for(int i = 0; i< formalParameter.size();i++){
                //check that function has liquid
                //=> all formal parameter are empty
                STentry entryF = Environment.lookup(e,formalParameter.get(i).getId());
                if(entryF instanceof STEntryAsset && ((STEntryAsset) entryF).getLiquidity() != 0){
                    System.out.println("La funzione "+id.getId()+" non e' liquida! [call]");
                    System.exit(0);
                }
            }
            e = Environment.exitScope(e);


        }
        return e;
    }
}
