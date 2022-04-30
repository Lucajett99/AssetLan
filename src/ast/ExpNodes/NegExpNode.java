package ast.ExpNodes;

import ast.Node;
import ast.typeNode.IntTypeNode;
import utils.Environment;
import utils.SemanticError;
import utils.Utilities;

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
        if(!(Utilities.isSubtype(exp.typeCheck(), new IntTypeNode()))) {
            System.out.println("Incompatible type error: Must Be IntTypeNode");
            System.exit(0);
        }
        return new IntTypeNode();
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
