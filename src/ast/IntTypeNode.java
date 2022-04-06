package ast;


import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class IntTypeNode implements Node {

    public IntTypeNode () {
    }

    public String toPrint(String s) {
        return s+"IntType\n";
    }

    //non utilizzato
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