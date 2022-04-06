package ast;

import ast.ExpNodes.BaseExpNode;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class PrintNode implements Node{
    private Node exp;

    public PrintNode(Node exp){
        this.exp = exp;
    }

    public String toPrint(String indent) {
        return "\n"+indent + "print"+ exp.toPrint(indent);
    }

    public Node typeCheck() {
        return exp.typeCheck();
    }

    public String codGeneration() {
        return exp.codGeneration();
    }

    public ArrayList<SemanticError> checkSemantics(Environment e) {
        return exp.checkSemantics(e);
    }
}
