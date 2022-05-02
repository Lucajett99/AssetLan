package ast;

import org.stringtemplate.v4.ST;
import utils.*;

import java.util.ArrayList;

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
        if(stEntry!= null
                && stEntry.isFunction()
                && params.size()==stEntry.getParameter().size()
                && bexp.size()==stEntry.getnAssets())
        {
            for(int i = 0; i< stEntry.getParameter().size();i++){
                Node ap = params.get(i);
                if(!Utilities.isSubtype(ap.typeCheck(),stEntry.getParameter().get(i).typeCheck())){
                    System.out.println("Incompatible Parameter for Function "+id.getId());
                    System.exit(0);
                }

            }
        }else {
            System.out.println("Incompatible Type Error [InitCall]");
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
}

