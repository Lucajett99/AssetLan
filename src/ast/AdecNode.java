package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class AdecNode implements Node {
    private ArrayList<IdNode> id;

    public AdecNode(ArrayList<IdNode> id) {
        this.id = id;
    }

    public ArrayList<IdNode> getId() {
        return id;
    }

    public void setId(ArrayList<IdNode> id) {
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        String str = "";
        for (IdNode id : id) {
            str += id.toPrint(indent) + " ";
        }
        return  indent +"Adec\n" + str;
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
