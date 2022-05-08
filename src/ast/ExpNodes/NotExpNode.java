package ast.ExpNodes;

import ast.Node;
import ast.typeNode.BoolTypeNode;
import ast.typeNode.IntTypeNode;
import utils.Environment;
import utils.SemanticError;
import utils.Utilities;

import java.util.ArrayList;

public class NotExpNode extends BaseExpNode {
    public NotExpNode(Node exp) {
        super(exp);
    }

    @Override
    public Node typeCheck() {
        if(!(Utilities.isSubtype(exp.typeCheck(), new BoolTypeNode()))) {
            System.out.println("Incompatible type error: Must Be BoolTypeNode");
            System.exit(0);
        }
        return new BoolTypeNode();
    }

    @Override
    public String codGeneration() {
        return super.codGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        return super.checkSemantics(e);
    }

    @Override
    public String toPrint(String indent) {
        return indent+"NotExp\n" + this.exp.toPrint(indent+"  ");
    }
}
