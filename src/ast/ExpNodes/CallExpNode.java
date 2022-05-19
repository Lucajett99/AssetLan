package ast.ExpNodes;

import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class CallExpNode implements Node {
    private Node call;

    public CallExpNode(Node call) {
        this.call = call;
    }

    @Override
    public String toPrint(String indent) {
        return indent+"CallExp\n" + call.toPrint(indent+"  ");
    }

    @Override
    public Node typeCheck() {
        return call.typeCheck();
    }

    @Override
    public String codGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        return new ArrayList<SemanticError>(call.checkSemantics(e));
    }

    @Override
    public Environment checkEffects(Environment e) {
        return null;
    }
}
