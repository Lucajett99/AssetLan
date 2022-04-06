package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class DecNode implements Node {
    private ArrayList<Node> type;
    private ArrayList<String> id;

    public DecNode(ArrayList<Node> type, ArrayList<String> id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        String str = "";
        str += type.get(0).toPrint(indent + " ");
        str += id.get(0) + indent + " ";
        if (type.get(1) != null && id.get(1) != null && type.size() == id.size())
            for (int i = 1; i < type.size(); i++) {  //starting from 1 because I have already printed it
                str += type.get(i).toPrint(indent + "  ");
                str += id.get(i) + indent + "  ";
            }
        return indent + "Dec\n" + str;
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
