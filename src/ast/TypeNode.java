package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class TypeNode implements Node{
    private String type;


    public TypeNode(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toPrint(String indent) {
       return indent + this.type + " ";
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
