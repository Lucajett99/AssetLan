package ast;

import ast.ExpNodes.BinExpNode;
import ast.ExpNodes.ValExpNode;
import ast.function.AdecNode;
import ast.function.DecpNode;
import ast.function.FunctionNode;
import ast.function.StatementNode;
import ast.typeNode.AssetTypeNode;
import ast.typeNode.IntTypeNode;
import utils.*;

import java.util.ArrayList;

public class InitCallNode implements Node{

    private IdNode id;
    private ArrayList<Node> params;
    private ArrayList<Node> bexp;
    private STentry stEntry;

    private int nestingLevel;
    public InitCallNode(IdNode id, ArrayList<Node> params, ArrayList<Node> bexp) {
        this.id = id;
        this.params = (params.size()>0?params:new ArrayList<>());
        this.bexp = (bexp.size()>0?bexp:new ArrayList<>());
        this.stEntry=null;
    }

    @Override
    public String toPrint(String indent) {
        String str =  indent+"InitCall\n";
        if(params!= null) {
            for (Node expnode : this.params) {
                str += expnode.toPrint(indent);
            }
        }
        if(bexp!= null) {
            for (Node idnode : this.bexp) {
                str += idnode.toPrint(indent);
            }
            ;
        }
        return str;
    }

    @Override
    public Node typeCheck() {
        if (stEntry != null &&(stEntry.getNode() instanceof FunctionNode)) {
            DecpNode formalDecParams = stEntry.getNode().getDecpNode();
            int lengthFormalDecPar = formalDecParams == null ? 0 : formalDecParams.getDecp().getListId().size();
            if (params.size() != lengthFormalDecPar)
                System.out.println("Incorrect Number of Params in Function " + id.getId());

            AdecNode formalAdecParams = stEntry.getNode().getADec();
            int lengthFormalAdecPar = formalAdecParams == null ? 0 : formalAdecParams.getId().size();
            if (bexp.size() !=  lengthFormalAdecPar)
                System.out.println("Incorrect Number of Asset in Function " + id.getId());
            //Check for each params if type is equal with type in ST
            if(formalDecParams != null) {
                for (int i = 0; i < lengthFormalDecPar; i++) {
                    Node actualParam = params.get(i);
                    if (!Utilities.isSubtype(actualParam.typeCheck(), formalDecParams.getDecp().getListType().get(i).typeCheck())) {
                        System.out.println("Incompatible Parameter for Function " + id.getId());
                        System.exit(0);
                    }
                }
            }
            //Check if all bexp are Int or Asset
            if(formalAdecParams != null && bexp.size() > 0) {
                for (int i = 0; i <lengthFormalAdecPar; i++) {
                    if (!Utilities.isSubtype(bexp.get(i).typeCheck(), new AssetTypeNode()) &&
                            !Utilities.isSubtype(bexp.get(i).typeCheck(), new IntTypeNode())) {
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
        String initCallCode = "push $fp \n";
        //I add the assets in inverse order
        for(int i = bexp.size(); i > 0 ; i--)
            initCallCode += bexp.get(i).codGeneration()
                    + "push $a0 \n";
        //I add the parameters in inverse order
        for(int i = params.size(); i > 0 ; i--)
            initCallCode += params.get(i).codGeneration()
                    + "push $a0 \n";
        //Now i will set the access link
        initCallCode += "lw $al 0($fp) \n";
        for(int i = 0; i < nestingLevel - stEntry.getNestingLevel(); i++)
            initCallCode += "lw $al 0($al) \n";
        initCallCode += "push $al";
        String label = stEntry.getNode().getFunLabel();
        initCallCode += "jal " + label; //jump at label and store the next instruction in ra
        return initCallCode + "print $b \n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(e.isDeclared(id.getId())== EnvError.NO_DECLARE){
            res.add(new SemanticError(id.getId()+": init function is not declared"));
        }else{
            stEntry = Environment.lookup(e,id.getId());
            nestingLevel = e.getNestingLevel();
        }
        if(bexp!= null){
            for (Node node:bexp) {
                res.addAll(node.checkSemantics(e));
            }
        }

        if(params!= null) {
            for (Node node : params) {
                res.addAll(node.checkSemantics(e));
            }
        }
        return res;
    }

    @Override
    public Environment checkEffects(Environment e) {
        STentry st = Environment.lookup(e,id.getId());
        e = Environment.newScope(e);
        if(st.getNode().getADec() != null){
            int index = 0;
            int number = 0;
            if(bexp.size() > 0){
                for( IdNode node : st.getNode().getADec().getId()) {
                    number = bexp.get(index).evaluateExp();
                    if (number != 0) {
                        e = Environment.addDeclaration(e, node.getId(), 1);
                    } else {
                        e = Environment.addDeclaration(e, node.getId(), 0);
                    }
                    index++;
                }
            }
        }
        if(st.getNode().getStatement() != null){
        ArrayList<StatementNode> stmList= st.getNode().getStatement();
            for(StatementNode stm : stmList){
                e = stm.checkEffects(e);
            }
        }
        if(st.getNode().getADec() != null){
            for( IdNode node : st.getNode().getADec().getId()){
                if(Environment.lookup(e,node.getId()).getLiquidity()!=0){
                    System.out.println("La funzione "+id.getId()+ " non é liquida!");
                    System.exit(0);
                };
            }
        }
        e = Environment.exitScope(e);

        return e;
    }
}

