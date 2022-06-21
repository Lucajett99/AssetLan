package ast.function;

import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class DecpNode implements Node {
    private DecNode decp;

    public DecNode getDecp() {
        return decp;
    }

    public void setDecp(DecNode decp) {
        this.decp = decp;
    }

    public DecpNode(DecNode decp) {
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
        return decp.checkSemantics(e);
    }
    @Override
    public Environment checkEffects(Environment e) {
        return null;
    }
}
