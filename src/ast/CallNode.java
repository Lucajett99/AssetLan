package ast;

import utils.*;

import java.util.ArrayList;

public class CallNode implements Node{
    private IdNode id;
    private ArrayList<Node> exp;
    private ArrayList<IdNode> listId;
    private STentry stEntry;

    public CallNode(IdNode id, ArrayList<Node> exp, ArrayList<IdNode> listId) {
        this.id = id;
        this.exp = exp;
        this.listId = (listId.size()>0?listId:new ArrayList<>());
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
        if(stEntry!= null
                && stEntry.isFunction()
                && exp.size()==stEntry.getParameter().size()
                && listId.size()==stEntry.getnAssets())
        {
            for(int i = 0; i< stEntry.getParameter().size();i++){
                Node ap = exp.get(i);
                if(!Utilities.isSubtype(ap.typeCheck(),stEntry.getParameter().get(i).typeCheck())){
                    System.out.println("Incompatible Parameter for Function "+id.getId());
                    System.exit(0);
                }

            }
        }else {
            System.out.println("Incompatible Type Error [Call]");
            System.exit(0);
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
            }
        }
        if(exp != null){
            for (Node exp:exp) {
                res.addAll(exp.checkSemantics(e));
            }
        }

        return res;
    }
}
