package ast;

import utils.EnvError;
import utils.Environment;
import utils.StEntry.STentry;
import utils.SemanticError;

import java.util.ArrayList;

public class IdNode implements Node{
    private String id;
    private Node type;

    private STentry sTentry;
    private int nestingLevel;

    public String getId() {
        return id;
    }

    public IdNode(String id) {
        this.id = id;
        this.type = null;
        this.sTentry = null;
        this.nestingLevel = -1;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "ID " + id + "\n";
    }


    @Override
    public Node typeCheck() {
        return this.type.typeCheck();
    }

    @Override
    public String codGeneration() {
        return null;
    }

    public String emptyValueCodGeneration(){
        //this function will be used in move and Transfer node to empty the register
        return  "li $a1 0\n" +
                "sw $a1 " + sTentry.getOffset() + "($al)\n";
    }

    public String storeCodGeneration() {
        String idCode = "mv $fp $al \n";
        for(int i = 0; i < nestingLevel - sTentry.getNestingLevel(); i++)
            idCode += "lw $al 0($al) \n";
        idCode += "sw $a0 " + sTentry.getOffset() + "($al) \n";
        return idCode;
    }

    public String accessCodGeneration(){
        String idCode = "mv $fp $al \n";
        for(int i = 0; i < nestingLevel - sTentry.getNestingLevel(); i++)
            idCode += "lw $al 0($al) \n";
        idCode += "lw $a0 " + sTentry.getOffset() + "($al) \n";
        return idCode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<>();
        if(e.isDeclared(this.id)== EnvError.NO_DECLARE) {
            res.add(new SemanticError((this.id) + ": is not declared "));
        }else {
            this.type = Environment.lookup(e, id).getType().typeCheck();
            this.sTentry = Environment.lookup(e, id);
            this.nestingLevel = e.getNestingLevel();
        }
        return res;
    }

    @Override
    public Environment checkEffects(Environment e) {
        return e;
    }
}
