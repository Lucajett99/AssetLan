package ast;

import utils.*;
import utils.StEntry.STEntryVar;

import java.util.ArrayList;

public class FieldNode implements Node {
    private TypeNode type;
    private IdNode id;
    private Node exp;

    private STEntryVar sTentry;


    public FieldNode(Node type, IdNode id, Node exp) {
        this.type = (TypeNode) type;
        this.id =  id;
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        String str="";
        str += type.toPrint(indent+"  ");
        str += id.toPrint(indent+"  ");
        str += exp != null ? exp.toPrint(indent+"  ") : "";
        return indent+"Field\n" + str;
    }

    @Override
    public Node typeCheck() {
        if(exp != null){
            if(! exp.typeCheck().getClass().equals(type.typeCheck().getClass())){
                System.out.println("Incompatible type error: field must be "+ type.getStringType());
                System.exit(0);
            }
        }
        return type.typeCheck();
    }

    @Override
    public String codGeneration() {
        String fieldCode = "";
        if (exp != null) {
            fieldCode += exp.codGeneration()
                      + "push $a0 \n";
        } else {
            fieldCode += "subi $sp $sp 1 \n";
        }
        return fieldCode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        if(e.checkHeadEnv(id.getId())!= EnvError.ALREADY_DECLARED){
            e = Environment.addDeclaration(e, e.setDecOffset(false), id.getId(), type.typeCheck());
            sTentry = (STEntryVar) Environment.lookup(e, id.getId());
        } else {
            res.add(new SemanticError(id.getId()+": already declared [field]"));
        }
        if(exp != null){
            res.addAll(this.exp.checkSemantics(e));
        }

        return res;
    }
    @Override
    public Environment checkEffects(Environment e) {
        return null;
    }//null because checkEffects mustn't be called
}
