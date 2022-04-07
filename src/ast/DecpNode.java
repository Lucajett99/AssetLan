package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class DecpNode implements Node{

    private Node decp;

    public DecpNode(Node decp) {
        this.decp = decp;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Decp\n"+ decp.toPrint(indent);
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
