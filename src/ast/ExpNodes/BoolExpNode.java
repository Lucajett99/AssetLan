package ast.ExpNodes;

import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class BoolExpNode implements Node {
    private boolean bool;

    public BoolExpNode(boolean bool) {
        this.bool = bool;
    }

    @Override
    public String toPrint(String indent) {
        return indent+"BoolExp\n" + bool;
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
