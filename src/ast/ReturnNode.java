package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class ReturnNode implements Node{
    private Node exp;

    public ReturnNode(Node exp) {
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return indent+" Return\n"+(exp==null?"null":exp.toPrint(indent));
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
        return exp.checkSemantics(e);
    }
}
