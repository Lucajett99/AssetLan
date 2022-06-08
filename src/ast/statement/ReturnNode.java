package ast.statement;

import ast.ExpNodes.CallExpNode;
import ast.Node;
import ast.function.FunctionNode;
import ast.typeNode.VoidTypeNode;
import utils.*;

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
        if(entry != null){
            if((exp == null && !Utilities.isSubtype(new VoidTypeNode(),getEntry().getType().typeCheck())) ||
                    (exp != null && !Utilities.isSubtype(exp.typeCheck(),entry.getType().typeCheck()))){
                System.out.println("Incompatible type of declaration method: must be a return "+ entry.getType().toPrint(""));
                System.exit(0);
            }
        }
        if(exp != null )
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

        if(exp instanceof CallExpNode){
            FunctionNode funNode = entry.getNode();
            if(entry != null) {
                if (((CallExpNode) exp).getCall() instanceof CallNode callnode)
                    if (funNode.getId().getId().equals(callnode.getId())) {
                        funNode.addRecursiveCall((CallNode) ((CallExpNode) exp).getCall());
                    }
            }
        }
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
