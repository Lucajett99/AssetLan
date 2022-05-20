package ast.statement;

import ast.ExpNodes.BaseExpNode;
import ast.Node;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class PrintNode implements Node {
    private Node exp;

    public PrintNode(Node exp){
        this.exp = exp;
    }

    public String toPrint(String indent) {
        return indent + "print\n"+ exp.toPrint(indent);
    }

    public Node typeCheck() {
        return exp.typeCheck();
    }

    public String codGeneration() {
        String printCode = exp.codGeneration()
                          +  "print $a0 \n" ;
        return printCode;
    }

    public ArrayList<SemanticError> checkSemantics(Environment e) {
        return exp.checkSemantics(e);
    }

    @Override
    public Environment checkEffects(Environment e) {
        return e;
    }
}
