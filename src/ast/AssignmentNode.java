package ast;

import utils.EnvError;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class AssignmentNode implements Node {
    private IdNode id;
    private Node exp;

    public AssignmentNode(IdNode ID, Node exp){
        this.id = ID;
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Assignment\n" + id + indent + exp.toPrint(indent+" ");
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
            res.add(new SemanticError(id.getId()+": is not declared"));
        }
        if(exp != null){
            res.addAll(exp.checkSemantics(e));
        }
    
        return res;
    }
}
