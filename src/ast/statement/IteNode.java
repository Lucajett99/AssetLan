package ast.statement;

import ast.ExpNodes.BaseExpNode;
import ast.Node;
import ast.typeNode.BoolTypeNode;
import ast.typeNode.VoidTypeNode;
import utils.Environment;
import utils.SemanticError;
import utils.Utilities;

import java.sql.Array;
import java.util.ArrayList;

public class IteNode implements Node {
    private Node exp;
    private ArrayList<Node> thenStatement;
    private ArrayList<Node> elseStatement;

    public IteNode(Node exp, ArrayList<Node> thenStatement, ArrayList<Node> elseStatement) {
        this.exp = exp;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }


    @Override
    public String toPrint(String indent) {
        String s1 = "";//to print then Statement
        String s2 = "";//to print else statement
        for (Node n : thenStatement) {
            s1 += n.toPrint(indent);
        }
        if(elseStatement != null){
            for(Node n: elseStatement){
                s2+=n.toPrint(indent);
            }
        }
        return " if ("+exp.toPrint(indent)+") then "+ s1 +" else "+ s2;
    }


    @Override
    public Node typeCheck() {
        if(!Utilities.isSubtype(exp.typeCheck(),new BoolTypeNode())){
               System.out.println("Incompatible Type Error : If condition must be boolean");
        }
        for(Node node : thenStatement){
            node.typeCheck();
        }
        if(elseStatement != null){
            for(Node node : elseStatement){
                node.typeCheck();
            }
        }

        return new VoidTypeNode();
    }

    @Override
    public String codGeneration() {
    /*    String iteCode = "";
        String trueLabel = Utilities.freshLabel();
        String endIfLabel = Utilities.freshLabel();
        iteCode += exp.codGeneration()
                + "beq $a0 1 " + trueLabel + "\n"  //TODO: CHECK IF CAN I DO THIS
                + elseStatement.codGeneration()
                + "b" + endIfLabel + " \n"
                + trueLabel + ": \n"
                + thenStatement.codGeneration()
                + endIfLabel + ": \n";
        return iteCode;*/
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        res.addAll(exp.checkSemantics(e));
        //non posso fare altre dichiarazioni per cui non ha senso entrare in un nuovo ambiente per eseguire la check Semantics
        for(Node node: thenStatement)
            res.addAll(node.checkSemantics(e));

        if(elseStatement!= null){
            for(Node node: elseStatement)
                res.addAll(node.checkSemantics(e));
        }
        return res;
    }

    @Override
    public Environment checkEffects(Environment e) {
        //Definire operatore Max(Environment e, Environment e1)
        return e;
    }
}
