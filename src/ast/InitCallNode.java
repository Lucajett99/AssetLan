package ast;

import utils.EnvError;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class InitCallNode implements Node{

    private IdNode id;
    private ArrayList<Node> params;
    private ArrayList<Node> bexp;

    public InitCallNode(IdNode id, ArrayList<Node> params, ArrayList<Node> bexp) {
        this.id = id;
        this.params = (params.size()>0?params:null);
        this.bexp = (bexp.size()>0?bexp:null);
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
        return null;
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

