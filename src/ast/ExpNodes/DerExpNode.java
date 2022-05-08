package ast.ExpNodes;

import ast.Node;
import org.stringtemplate.v4.ST;
import utils.EnvError;
import utils.Environment;
import utils.STentry;
import utils.SemanticError;

import java.util.ArrayList;

public class DerExpNode implements Node {
    private String id;
    private Node type;
    private STentry sTentry;
    private int nestingLevel;
    public DerExpNode(String id) {
        this.id = id;
        this.type = null;
        this.sTentry = null;
        this.nestingLevel = -1;
    }

    @Override
    public String toPrint(String indent) {
        return indent+"DerExp " + id + "\n";
    }

    @Override
    public Node typeCheck() {
        return type.typeCheck();
    }

    @Override
    public String codGeneration() {
        String derExpCode = "";
        int nestingLevel = sTentry.getNestingLevel();
        for (int i = 0; i < nestingLevel - sTentry.getNestingLevel(); i++)
            derExpCode+= "lw $al 0($al) \n";
        derExpCode += "lw $al" + sTentry.getNestingLevel() +"\n";
        return derExpCode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(e.isDeclared(id)== EnvError.NO_DECLARE){
            res.add(new SemanticError(id+": variable is not declared [DerExp]"));
        }
        else {
            type = Environment.lookup(e,id).getType();
            this.sTentry = Environment.lookup(e, id);
            nestingLevel = e.getNestingLevel();
        }
        return res;
    }
}
