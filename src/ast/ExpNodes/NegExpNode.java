package ast.ExpNodes;

import ast.Node;
import ast.typeNode.IntTypeNode;
import utils.Environment;
import utils.Utilities;

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
        if(!(exp.typeCheck().equals(new IntTypeNode()))) {
            System.out.println("Incompatible type error: Must Be IntTypeNode");
            System.exit(0);
        }
        return new IntTypeNode();
    }

    @Override
    public String codGeneration() {
        String negCode = "";
        negCode += exp.codGeneration()
                + "multi $a0 $a0 -1 \n";
        return  negCode;

    }

    @Override
    public Environment checkEffects(Environment e) {
        return e;
    }

    @Override
    public int evaluateExp() {
        return -exp.evaluateExp();
    }
}

