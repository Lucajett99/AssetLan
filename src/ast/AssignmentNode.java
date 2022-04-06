package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class AssignmentNode implements Node {
    private String id;
    private Node exp;

    public AssignmentNode(String ID, Node exp){
        this.id = ID;
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return "\n"+indent + "Assignment " + id + indent + " = " + exp.toPrint(indent+" ");
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
        return null;
    }
}
