package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class TransferNode implements Node{
    private IdNode id;

    public TransferNode(IdNode id) {
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        return "\n"+"transfer: "+id.toPrint(indent);
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
