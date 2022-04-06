package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class AdecNode implements Node {
    private ArrayList<String> id;

    public AdecNode(ArrayList<String> id) {
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        String str = "";
        for (String id : id) {
            str += id + indent + " ";
        }
        return  indent +"\nAdec " + str;
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
