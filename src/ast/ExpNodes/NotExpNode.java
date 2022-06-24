package ast.ExpNodes;

import ast.Node;
import ast.typeNode.BoolTypeNode;
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
        if(!(exp.typeCheck().getClass().equals(new BoolTypeNode().getClass()))) {
            System.out.println("Incompatible type error: Must Be BoolTypeNode");
            System.exit(0);
        }
        return new BoolTypeNode();
    }

    @Override
    public String codGeneration() {
        String notExp = "";
        notExp += exp.codGeneration()
                + "not $a0 $a0 \n";
        return notExp;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        return super.checkSemantics(e);
    }

    @Override
    public Environment checkEffects(Environment e) {
        return e;
    }
}
