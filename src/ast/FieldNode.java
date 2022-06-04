package ast;

import utils.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FieldNode implements Node {
    private TypeNode type;
    private IdNode id;
    private Node exp;

    private STentry sTentry;


    public FieldNode(Node type, IdNode id, Node exp) {
        this.type = (TypeNode) type;
        this.id =  id;
        this.exp = exp;
    }

    @Override
    public String toPrint(String indent) {
        String str="";
        str += type.toPrint(indent+"  ");
        str += id.toPrint(indent+"  ");;
        str += exp != null ? exp.toPrint(indent+"  ") : "";
        return indent+"Field\n" + str;
    }

    @Override
    public Node typeCheck() {
        if(exp != null){
            if(!Utilities.isSubtype(exp.typeCheck(), type.typeCheck())){
                System.out.println("Incompatible type error: field must be "+ type.getStringType() +" [Field]");
            }
        }
        return type.typeCheck();
    }
    // We only generate the code if the expNode exist
    @Override
    public String codGeneration() {
        String fieldCode = "";
        // I will only use the STentry  because I declare and inizialize variable at same time
        if (exp != null) {
            fieldCode += exp.codGeneration() +
                         //"sw $a0 " + sTentry.getOffset()+"($fp) \n";
                        "push $a0 \n"; //TODO: remember to change
        } else {
            fieldCode += "subi $sp $sp 1 \n";
        }
        return fieldCode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        if(e.isMultipleDeclared(id.getId())!= EnvError.ALREADY_DECLARED){
            e = Environment.addDeclaration(e, e.setDecOffset(false), id.getId(), type);
            sTentry = e.lookup(e, id.getId());
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
