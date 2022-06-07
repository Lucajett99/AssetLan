package ast.ExpNodes;

import ast.typeNode.BoolTypeNode;
import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class BoolExpNode implements Node {
    private final boolean bool;

    public BoolExpNode(boolean bool) {
        this.bool = bool;
    }

    @Override
    public String toPrint(String indent) {
        return indent+"BoolExp\n" + bool;
    }

    @Override
    public Node typeCheck() {

        return new BoolTypeNode();
    }

    @Override
    public String codGeneration() {
        String boolExpCode = "";
        if(bool == true)
            boolExpCode += "li $a0 1 \n";
        else
            boolExpCode += "li $a0 0 \n";
        return boolExpCode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        return new ArrayList<SemanticError>();
    }

    @Override
    public Environment checkEffects(Environment e) {
        return e;
    }
}
