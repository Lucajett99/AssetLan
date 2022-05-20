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

import java.util.ArrayList;

public class CallNode implements Node {
    private IdNode id;
    private ArrayList<Node> exp;
    private ArrayList<IdNode> listId;
    private STentry stEntry;

    public CallNode(IdNode id, ArrayList<Node> exp, ArrayList<IdNode> listId) {
        this.id = id;
        this.exp = exp; //actualDecParams
        this.listId = (listId.size()>0?listId:new ArrayList<>()); //actualAdecParams
        this.stEntry = null;
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
            if (exp.size() != lengthFormalDecPar)
                System.out.println("Incorrect Number of Params in Function " + id.getId());

            AdecNode formalAdecParams = stEntry.getNode().getADec();
            int lengthFormalAdecPar = formalAdecParams == null ? 0 : formalAdecParams.getId().size();
            if (listId.size() !=  lengthFormalAdecPar)
                System.out.println("Incorrect Number of Asset in Function " + id.getId());
            //Check for each params if type is equal with type in ST
            if(formalDecParams != null) {
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
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        if(e.isDeclared(id.getId())== EnvError.NO_DECLARE){
            res.add(new SemanticError(id.getId()+": function is not declared [Call]"));
        }else{
            stEntry = Environment.lookup(e,id.getId());
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
        STentry st = Environment.lookup(e,id.getId());
        ArrayList<StatementNode> stmList= st.getNode().getStatement();
        e = Environment.newScope(e);

        //Identificatori Asset

        //Sappiamo per definizione che size dei parametri formali == size parametri attuali
        ArrayList<IdNode> actualParameter = listId != null ? listId : new ArrayList<>();
        ArrayList<IdNode> formalParameter = st.getNode().getADec() != null ? st.getNode().getADec().getId() : new ArrayList<>();

        for (int i = 0; i < actualParameter.size(); i++) {
            //aggiorno l'attuale in base al formale
            STentry entryA = Environment.lookup(e, actualParameter.get(i).getId());
            //STentry entryF = Environment.lookup(e,formalParameter.get(i).getId());
            Environment.addDeclaration(e, formalParameter.get(i).getId(), entryA.getLiquidity());
            entryA.setLiquidity(0);
        }

        if(stmList != null){
            for(StatementNode stm : stmList){
                if(stm.getStatement() instanceof CallNode && ((CallNode) stm.getStatement()).id == this.id) {
                    //chiamata ricorsiva presumiamo ci voglia il punto fisso
                }
                e = stm.checkEffects(e);
            }
        }

        for(int i = 0; i< formalParameter.size();i++){
            //check that function has liquid
            //=> all formal parameter are empty
            STentry entryF = Environment.lookup(e,formalParameter.get(i).getId());
            if(entryF.getLiquidity() != 0){
                System.out.println("funzione "+id.getId()+" non é liquida!");
                System.exit(0);
            }
        }
        e = Environment.exitScope(e);

        return e;
    }
}
