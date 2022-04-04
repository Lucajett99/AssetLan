package ast;

import utils.Environment;
import utils.SemanticError;
import java.util.ArrayList;

public class FieldNode implements Node {
    Node type;
    String id;
    Node exp;

    public FieldNode(Node type, String id, Node exp) {
        this.type = type;
        this.id = id;
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        String str="";
        str += type.toPrint(indent+"  ");
        str += id;
        str += exp.toPrint(indent+"  ");
        return indent+"Program\n" + str;
    }

    @Override
    public Node typeCheck() {
        return null;
    }

    @Override
    public String codGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        return null;
    }
}
