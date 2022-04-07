package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class FunctionNode implements Node {
    private Node type;
    private String id;
    private Node decp;
    private ArrayList<Node> dec;
    private Node adec;
    private ArrayList<Node> statement;

    public FunctionNode(Node type, String id,Node decp, ArrayList<Node> dec, Node adec, ArrayList<Node> statement) {
        this.type = type;
        this.decp = (decp);
        this.id = id;
        this.dec = dec;
        this.adec = adec;
        this.statement = statement;
    }

    @Override
    public String toPrint(String indent) {
        String str="";
        str += type.toPrint(indent + " ") + id + " ";
        if(!(decp ==null))
            str += decp.toPrint(indent + " ");
        if(adec != null)
            str += adec.toPrint(indent + " ");
        if(dec.get(1) != null)
            for (int i = 1; i < dec.size(); i++)  //starting from 1 because I have already printed it
                str += dec.get(i).toPrint(indent + "  ");
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
