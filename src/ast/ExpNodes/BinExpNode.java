package ast.ExpNodes;

import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class BinExpNode implements Node {
    private String op;
    private Node left;
    private Node right;

    public BinExpNode(String op, Node left, Node right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toPrint(String indent) {
        String str="";
        str += left.toPrint(indent+"  ");
        str += op;
        str += left.toPrint(indent+"  ");
        return indent+"BinExp\n" + str;
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
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(left!=null){
            res.addAll(left.checkSemantics(e));
        }
        if(right !=null){
            res.addAll(right.checkSemantics(e));
        }
        return res;
    }
}
