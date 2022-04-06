package ast;


import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class BoolTypeNode implements Node {

    public BoolTypeNode () {
    }

    public String toPrint(String s) {
        return s+"BoolType\n";
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