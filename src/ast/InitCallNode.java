package ast;

import ast.typeNode.AssetTypeNode;
import ast.typeNode.IntTypeNode;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STErrorListener;
import utils.*;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.List;

public class InitCallNode implements Node{

    private IdNode id;
    private ArrayList<Node> params;
    private ArrayList<Node> bexp;
    private STentry stEntry;
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
            if(formalAdecParams != null) {
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
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(e.isDeclared(id.getId())== EnvError.NO_DECLARE){
            res.add(new SemanticError(id.getId()+": init function is not declared"));
        }else{
            stEntry = Environment.lookup(e,id.getId());
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
        for( IdNode node : st.getNode().getADec().getId()){
            e = Environment.addDeclaration(e,node.getId(),1);
        }
        //stmLists non sará mai null poiché non esistono funzioni senza corpo
        ArrayList<StatementNode> stmList= st.getNode().getStatement();
        for(StatementNode stm : stmList){
            e = stm.checkEffects(e);
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

