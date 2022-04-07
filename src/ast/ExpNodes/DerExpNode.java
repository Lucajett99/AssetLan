package ast.ExpNodes;

import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class DerExpNode implements Node {
    private String id;

    public DerExpNode(String id) {
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        return indent+"DerExp\n" + id;
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
