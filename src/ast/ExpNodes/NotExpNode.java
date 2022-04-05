package ast.ExpNodes;

import ast.Node;

public class NotExpNode extends BaseExpNode {
    public NotExpNode(Node exp) {
        super(exp);
    }

    @Override
    public String toPrint(String indent) {
        return indent+"NotExp\n" + this.exp.toPrint(indent+"  ");
    }
}
