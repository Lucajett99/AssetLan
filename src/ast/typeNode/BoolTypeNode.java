package ast.typeNode;

import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class BoolTypeNode implements Node {

    public String toPrint(String s) {
        return s+" BoolType\n";
    }

    @Override
    public Node typeCheck() {
        return this;
    }

    @Override
    public String codGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        return new ArrayList<SemanticError>();
    }

    @Override
    public Environment checkEffects(Environment e) {
        return null;
    }
}
