package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class MoveNode implements Node{

    private IdNode id1;
    private IdNode id2;

    public MoveNode(IdNode id1, IdNode id2) {
        this.id1 = id1;
        this.id2 = id2;
    }


    @Override
    public String toPrint(String indent) {
        return indent+" transfer: "+id1.toPrint(indent)+" "+id2.toPrint(indent);
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
