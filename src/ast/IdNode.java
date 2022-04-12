package ast;

import utils.Environment;
import utils.STentry;
import utils.SemanticError;

import java.util.ArrayList;

public class IdNode implements Node{
    private String id;

    public String getId() {
        return id;
    }

    public IdNode(String id) {
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "ID " + id + "\n";
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
