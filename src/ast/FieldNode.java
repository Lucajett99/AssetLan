package ast;

import utils.Environment;
import utils.SemanticError;
import java.util.ArrayList;

public class FieldNode implements Node {
    private TypeNode type;
    private IdNode id;
    private Node exp;

    public FieldNode(Node type, IdNode id, Node exp) {
        this.type = (TypeNode) type;
        this.id =  id;
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        String str="";
        str += type.toPrint(indent+"  ");
        str += id;
        str += exp.toPrint(indent+"  ");
        return indent+"Field\n" + str;
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
