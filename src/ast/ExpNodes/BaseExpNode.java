package ast.ExpNodes;

import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class BaseExpNode implements Node {
    protected Node exp;

    public BaseExpNode(Node exp) {
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return indent+"BaseExp\n" + exp.toPrint(indent+"  ");
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
