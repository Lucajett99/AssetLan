package ast;

import utils.Environment;
import utils.STentry;
import utils.SemanticError;

import java.util.ArrayList;

public class IdNode implements Node{
    private String id;
    private Node type;

    private STentry sTentry;
    private int nestingLevel;

    public String getId() {
        return id;
    }

    public IdNode(String id) {
        this.id = id;
        this.type = null;
        this.sTentry = null;
        this.nestingLevel = -1;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "ID " + id + "\n";
    }

    @Override
    public Node typeCheck() {
        return this.type;
    }

    @Override
    public String codGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<>();
        this.type = Environment.lookup(e, id).getType().typeCheck();
        this.sTentry = Environment.lookup(e, id);
        this.nestingLevel = e.getNestingLevel();

        return res;
    }

    @Override
    public ArrayList<String> checkEffects(Environment e) {
        return null;
    }
}
