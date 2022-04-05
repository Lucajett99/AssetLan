package ast.ExpNodes;

import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class NegExpNode extends BaseExpNode {

    public NegExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toPrint(String indent) {
        return indent+"NegExp\n" + this.exp.toPrint(indent+"  ");
    }

    @Override
    public Node typeCheck() {
        return super.typeCheck();
    }

    @Override
    public String codGeneration() {
        return super.codGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        return super.checkSemantics(e);
    }
}
