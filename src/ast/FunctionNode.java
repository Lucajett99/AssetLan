package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class FunctionNode implements Node {
    private TypeNode type;
    private IdNode id;
    private Node decp;
    private ArrayList<Node> dec;
    private Node adec;
    private ArrayList<Node> statement;

    public FunctionNode(Node type, IdNode id, Node decp, ArrayList<Node> dec, Node adec, ArrayList<Node> statement) {
        if (type != null) {
            this.type = (TypeNode) type;
        }
        else
            this.type = new TypeNode("void");
        this.decp = (decp);
        this.id = id;
        this.dec = dec;
        this.adec = adec;
        this.statement = statement;
    }

    @Override
    public String toPrint(String indent) {
        String str="";
        str += type.toPrint(indent + " ") + id.toPrint(indent + " ");
        if(!(decp ==null))
            str += decp.toPrint(indent + " ");
        if(adec != null)
            str += adec.toPrint(indent + " ");
        for (Node dec : dec)
                str += dec.toPrint(indent + "  ");
        for (Node statement : statement)
            str += statement.toPrint(indent+"  ");

        return indent+"Function\n" + str;
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
