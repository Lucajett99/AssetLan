package ast.typeNode;

import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class AssetTypeNode implements Node {
    @Override
    public String toPrint(String indent) {
        return null;
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
        return new ArrayList<SemanticError>();
    }
}
