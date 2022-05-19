package ast.ExpNodes;

import ast.typeNode.IntTypeNode;
import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class ValExpNode implements Node {
    private int number;

    public ValExpNode(int number) {
        this.number = number;
    }

    @Override
    public String toPrint(String indent) {
        return indent+"ValExp" + indent + number + "\n";
    }

    @Override
    public Node typeCheck() {
        return new IntTypeNode();
    }

    @Override
    public String codGeneration() {
        return "li $a0 " + number + " \n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        return new ArrayList<SemanticError>();
    }

    @Override
    public Environment checkEffects(Environment e) {
        return null;
    }
}
