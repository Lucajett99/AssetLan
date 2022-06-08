package ast.statement;

import ast.Node;
import ast.typeNode.VoidTypeNode;
import utils.Environment;
import utils.STEntryFun;
import utils.STentry;
import utils.SemanticError;

import java.util.ArrayList;

public class ReturnNode implements Node {
    private Node exp;
    private STEntryFun entry;
    private int nestingLevel;

    public ReturnNode(Node exp) {
        this.exp = exp;
        this.entry = null;
        this.nestingLevel = -1;
    }

    public STEntryFun getEntry() {
        return entry;
    }

    public void setEntry(STEntryFun entry) {
        this.entry = entry;
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
        String retCode = "";
        if(exp != null)
            retCode += exp.codGeneration();
        for(int i = 0; i< nestingLevel - entry.getNestingLevel(); i++)
            retCode += "lw $fp 0($fp)\n";

        retCode += "lw $fp 0($fp) //Load old $fp pushed \n"
                 +"b "+entry.getNode().getEndLabel()+" \n";

        return retCode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        nestingLevel = e.getNestingLevel();
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
