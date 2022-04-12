package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;
import java.util.Iterator;

public class DecNode implements Node {
    private ArrayList<TypeNode> type;
    private ArrayList<IdNode> id;

    public DecNode(ArrayList<TypeNode> type, ArrayList<IdNode> id) {
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
        String str = "";
            for (int i = 0; i < type.size(); i++) {
                str += type.get(i).toPrint(indent + "  ");
                str += id.get(i).toPrint(indent + " ");
            }
        return indent + "Dec " + str + "\n";
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
