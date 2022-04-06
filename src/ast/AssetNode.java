package ast;

import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class AssetNode implements Node {
    private String id;

    public AssetNode(String id) {
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        return indent+"Asset\n" + id;
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
