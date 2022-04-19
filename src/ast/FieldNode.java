package ast;

import utils.EnvError;
import utils.Environment;
import utils.SemanticError;

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
        return null;
    }

    @Override
    public String codGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        if(e.isMultipleDeclared(id.getId())!= EnvError.ALREADY_DECLARED){
            e = Environment.addDeclaration(e,id.getId(), type.getType());
        }else{
            res.add(new SemanticError(id.getId()+": already declared [field]"));
        }

        if(exp != null){
            res.addAll(this.exp.checkSemantics(e));
        }

        return res;
    }
}
