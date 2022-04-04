package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class ProgramNode implements Node {
    private ArrayList<Node> fields;
    private ArrayList<Node> assets;
    private ArrayList<Node> functions;
    private Node initcall;

    public ProgramNode(ArrayList<Node> f, ArrayList<Node> a, ArrayList<Node> fun, Node i) {
        fields = f;
        assets = a;
        functions = fun;
        initcall = i;
    }

    @Override
    public String toPrint(String indent) {
        String str="";
        for (Node field:fields)
            str += field.toPrint(indent+"  ");
        for (Node asset:assets)
            str += asset.toPrint(indent+"  ");
        for (Node function:functions)
            str += function.toPrint(indent+"  ");
        return indent+"Program\n" + str + initcall.toPrint(indent+"  ") ;
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
    public ArrayList<SemanticError> checkSemantics(Environment e) { return null; }
}

