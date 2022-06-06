package ast.statement;

import ast.ExpNodes.BaseExpNode;
import ast.Node;
import ast.function.FunctionNode;
import ast.function.StatementNode;
import ast.typeNode.BoolTypeNode;
import ast.typeNode.VoidTypeNode;
import utils.Environment;
import utils.LiquidityUtils;
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

    public ArrayList<Node> getThenStatement() {
        return this.thenStatement;
    }

    public ArrayList<Node> getElseStatement() {
        return this.elseStatement;
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
        //Definire operatore Max(Environment e, Environment e1)

        Environment e1 = e.clone();
        Environment e2 = e.clone();
        if(elseStatement == null){
            for(Node node : thenStatement) {
                StatementNode stmNode = (StatementNode) node;

                if (stmNode.getStatement() instanceof CallNode) {
                    CallNode cnode = (CallNode) stmNode.getStatement();
                    FunctionNode fnode = Environment.lookup(e1,cnode.getId()).getNode();
                    e1 = LiquidityUtils.fixPointMethod(e, fnode, cnode);
                }else{
                    e1 = node.checkEffects(e1);
                }
            }
            return Environment.max(e,e1);
        }else{
            ArrayList<Node> aggregateStm = new ArrayList<Node>();
            aggregateStm.addAll(thenStatement);
            aggregateStm.addAll(elseStatement);

            for(Node node : aggregateStm) {
                StatementNode nodeStatement = (StatementNode) node;
                if (nodeStatement.getStatement() instanceof CallNode) {
                    CallNode cnode = (CallNode)nodeStatement.getStatement();
                    FunctionNode fnode = Environment.lookup(e,cnode.getId()).getNode();
                    return LiquidityUtils.fixPointMethod(e, fnode, cnode);
                }
            }

            for(Node node : thenStatement){
                e1 = node.checkEffects(e1);
            }
            for(Node node : elseStatement){
                e2 = node.checkEffects(e2);
            }

            return Environment.max(e1,e2);
        }
    }
}
