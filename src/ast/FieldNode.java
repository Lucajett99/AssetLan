package ast;

import utils.EnvError;
import utils.Environment;
import utils.SemanticError;
import utils.Utilities;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FieldNode implements Node {
    private TypeNode type;
    private IdNode id;
    private Node exp;

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
    // We only generate the code of the exp node if it exist
    @Override
    public String codGeneration() {
        String fieldCode = "";
        if (exp != null)
            fieldCode += exp.codGeneration();
        return fieldCode;
        //TODO: I have to generate more code?
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        if(e.isMultipleDeclared(id.getId())!= EnvError.ALREADY_DECLARED){
            e = Environment.addDeclaration(e, e.setDecOffset(), id.getId(), type);
        }else{
            res.add(new SemanticError(id.getId()+": already declared [field]"));
        }

        if(exp != null){
            res.addAll(this.exp.checkSemantics(e));
        }

        return res;
    }
}
