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
        return exp.typeCheck();
    }

    @Override
    public String codGeneration() {
        return exp.codGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        return exp.checkSemantics(e);
    }

    @Override
    public Environment checkEffects(Environment e) {
        return exp.checkEffects(e);
    }

    @Override
    public int evaluateExp() {
        return exp.evaluateExp();
    }
}
