package ast.statement;

import ast.IdNode;
import ast.Node;
import ast.function.FunctionNode;
import ast.function.StatementNode;
import ast.typeNode.BoolTypeNode;
import ast.typeNode.VoidTypeNode;
import utils.*;
import utils.StEntry.STEntryAsset;
import utils.StEntry.STEntryFun;

import java.util.ArrayList;
import java.util.Objects;

public class IteNode implements Node {
    private Node exp;
    private ArrayList<Node> thenStatement;
    private ArrayList<Node> elseStatement;

    public IteNode(Node exp, ArrayList<Node> thenStatement, ArrayList<Node> elseStatement) {
        this.exp = exp;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    public ArrayList<Node> getThenStatement() {
        return this.thenStatement;
    }

    public ArrayList<Node> getElseStatement() {
        return this.elseStatement;
    }

    public boolean containsReturn(){
        boolean isPresent = false ;
        if(elseStatement != null)
            for(Node node : elseStatement){
                StatementNode stm = (StatementNode)  node;
                if(stm.getStatement() instanceof ReturnNode){isPresent = true;}
                else if(stm.getStatement() instanceof IteNode && ((IteNode)stm.getStatement()).containsReturn() ){isPresent=true;}
            }
        for(Node node : thenStatement){
            StatementNode stm = (StatementNode)  node;
            if(isPresent && stm.getStatement() instanceof ReturnNode ) return true;
            else if(isPresent && stm.getStatement() instanceof IteNode && ((IteNode)stm.getStatement()).containsReturn()) return true;
        }
        return false;
    }

    public Node getExp(){return exp;}
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
        if(!exp.typeCheck().getClass().equals(new BoolTypeNode().getClass())){
               System.out.println("Incompatible Type Error : If condition must be boolean");
               System.exit(0);
        }
        for(Node node : thenStatement){
            node.typeCheck();
        }
        if(elseStatement != null){
            for(Node node : elseStatement){
                node.typeCheck();
            }
        }

        return null;
    }

    @Override
    public String codGeneration() {
        String iteCode = "";
        String falseLabel = Utilities.freshLabel();
        String endIfLabel = Utilities.freshLabel();
        iteCode += exp.codGeneration()
                + "li $a1 0\n"
                + "beq $a0 $a1 " + falseLabel + "\n // START THEN BRANCH IF STATEMENT \n";
        for(Node node : thenStatement)
            iteCode += node.codGeneration();
        iteCode   += "b " + endIfLabel + " \n"
                  + falseLabel + ": \n  // START ELSE BRANCH IF STATEMENT \n";
        if (elseStatement != null)
            for(Node node : elseStatement)
                iteCode += node.codGeneration();

        iteCode += endIfLabel + ": //END IF \n";
        return iteCode;

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

        e = exp.checkEffects(e);

        Environment e1 = e.clone();
        Environment e2 = e.clone();

        for(Node node : thenStatement) {
            StatementNode stmNode = (StatementNode) node;
            //controllo se c'è una chiamata ed è ricorsiva
            if (stmNode.getStatement() instanceof CallNode callNode && stmNode.getFunNode().getId().getId().equals(callNode.getId())) {
                ArrayList<StatementNode> stmList = new ArrayList<StatementNode>();
                for(Node stm : thenStatement) {
                    stmList.add((StatementNode) stm);
                }
                Utilities.nCallRecursive(callNode.getId(),stmList);

                FunctionNode funNode = stmNode.getFunNode();
                if(!LiquidityUtils.fixPoint) return LiquidityUtils.fixPointMethod(e1, callNode.getListId(),funNode, callNode);
            }
            else {
                e1 = node.checkEffects(e1);
            }
        }

        if(elseStatement != null) {
            for (Node node : elseStatement) {
                StatementNode stmNode = (StatementNode) node;
                //controllo se c'è una chiamata ed è ricorsiva
                if (stmNode.getStatement() instanceof CallNode callNode && stmNode.getFunNode().getId().getId().equals(callNode.getId())) {
                    ArrayList<StatementNode> stmList = new ArrayList<StatementNode>();
                    for(Node stm : elseStatement) {
                        stmList.add((StatementNode) stm);
                    }
                    Utilities.nCallRecursive(callNode.getId(),stmList);

                    FunctionNode funNode = stmNode.getFunNode();
                    if(!LiquidityUtils.fixPoint) return LiquidityUtils.fixPointMethod(e2, callNode.getListId(), funNode, callNode);
                } else {
                    e2 = node.checkEffects(e2);
                }
            }
        }
        return LiquidityUtils.max(e1,e2);
    }
}
