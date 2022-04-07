package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;
import java.util.Iterator;

public class DecNode implements Node {
    private ArrayList<Node> type;
    private ArrayList<IdNode> id;

    public DecNode(ArrayList<Node> type, ArrayList<IdNode> id) {
        if(type.size() == id.size()) {
            this.type = type;
            this.id = id;
        }else{
            this.type = null;
            this.id = null;
        }
    }

    @Override
    public String toPrint(String indent) {
        return null;
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