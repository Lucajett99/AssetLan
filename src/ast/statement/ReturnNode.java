package ast.statement;

import ast.Node;
import ast.typeNode.VoidTypeNode;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class ReturnNode implements Node {
    private Node exp;

    public ReturnNode(Node exp) {
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        return indent+" Return\n"+(exp==null?"null":exp.toPrint(indent));
    }

    @Override
    public Node typeCheck() {
        if(exp != null)
            return exp.typeCheck();
        else return new VoidTypeNode();
    }

    @Override
    public String codGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(exp != null)
            return exp.checkSemantics(e);
        else
            return res;
    }

    @Override
    public Environment checkEffects(Environment e) {
        return e;
    }
}
