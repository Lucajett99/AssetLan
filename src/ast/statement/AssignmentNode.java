package ast.statement;

import ast.IdNode;
import ast.Node;
import utils.EnvError;
import utils.Environment;
import utils.SemanticError;
import utils.Utilities;

import java.util.ArrayList;

public class AssignmentNode implements Node {
    private IdNode id;
    private Node exp;
    private Node type;

    public AssignmentNode(IdNode ID, Node exp){
        this.id = ID;
        this.exp = exp;
        this.type = null;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Assignment\n" + id.toPrint(indent) + indent + exp.toPrint(indent+" ");
    }

    @Override
    public Node typeCheck() {
        if(!Utilities.isSubtype(type.typeCheck(), exp.typeCheck())){
            System.out.println("Incompatible type expression : required "+type.typeCheck());
            System.exit(0);
        }
        return type.typeCheck();
    }

    @Override
    public String codGeneration() {
        String assignmentCode = "";
        assignmentCode += exp.codGeneration()
                       + id.codGeneration()
                       + "sw $a0 0($al)";
        return assignmentCode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(e.isDeclared(id.getId())== EnvError.NO_DECLARE){
            res.add(new SemanticError(id.getId()+": is not declared"));
        }else{
            type = Environment.lookup(e,id.getId()).getType();
        }
        if(exp != null){
            res.addAll(exp.checkSemantics(e));
        }
    
        return res;
    }

    @Override
    public Environment checkEffects(Environment e) {
        return e;
    }
}
