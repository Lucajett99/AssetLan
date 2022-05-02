package ast;

import ast.ExpNodes.BaseExpNode;
import ast.typeNode.BoolTypeNode;
import utils.Environment;
import utils.SemanticError;
import utils.Utilities;

import java.sql.Array;
import java.util.ArrayList;

public class IteNode implements Node{
    private Node exp;
    private Node thenStatement;
    private Node elseStatement;

    public IteNode(Node exp, Node thenStatement, Node elseStatement) {
        this.exp = exp;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }


    @Override
    public String toPrint(String indent) {
        return indent+" if ("+exp.toPrint(indent)+ ") then "+thenStatement.toPrint(indent)+
                (elseStatement==null?" ":" else "+ elseStatement.toPrint(indent));
    }

    @Override
    public Node typeCheck() {
        if(!Utilities.isSubtype(exp.typeCheck(),new BoolTypeNode())){
                System.out.println("Incompatible Type Error : If condition must be boolean");
        }
        if(elseStatement == null){
            return thenStatement.typeCheck();
        }else{
            if(Utilities.isSubtype(thenStatement.typeCheck(),elseStatement.typeCheck()))
                return thenStatement.typeCheck();
            else
                System.out.println("Incompatible Type Error: else/then stm must return the same type");
        }

        return null;
    }

    @Override
    public String codGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        res.addAll(exp.checkSemantics(e));
        res.addAll(thenStatement.checkSemantics(e));
        if(elseStatement!= null){ res.addAll(elseStatement.checkSemantics(e));}
        return res;
    }
}
