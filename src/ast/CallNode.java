package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class CallNode implements Node{
    private IdNode id;
    private ArrayList<Node> exp;
    private ArrayList<IdNode> listId;

    public CallNode(IdNode id, ArrayList<Node> exp, ArrayList<IdNode> listId) {
        this.id = id;
        this.exp = exp;
        this.listId = listId;
    }


    @Override
    public String toPrint(String indent) {
        String str ="\n" + indent + "call :" + id.toPrint(indent);
        for (Node expnode : this.exp) {
            str+=expnode.toPrint(indent);
        }
        for (Node idnode : this.listId) {
            str+=idnode.toPrint(indent);
        }

        return str;
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
