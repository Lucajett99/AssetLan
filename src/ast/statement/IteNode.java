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
        if(!Utilities.isSubtype(exp.typeCheck(),new BoolTypeNode())){
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

    public ArrayList<CallNode> checkCall(){
        ArrayList<CallNode> call = new ArrayList<CallNode>();

        for(Node stm : thenStatement){
            StatementNode statement = (StatementNode)stm;
            if(statement .getStatement() instanceof CallNode){ call.add((CallNode) statement.getStatement());}
            if(statement.getStatement() instanceof IteNode){
                call.addAll(((IteNode) statement.getStatement()).checkCall());
            }
        }
        if(elseStatement != null){
            for(Node stm : elseStatement){
                StatementNode statement = (StatementNode)stm;
                if(statement .getStatement() instanceof CallNode){ call.add((CallNode) statement.getStatement());}
                if(statement.getStatement() instanceof IteNode){
                    call.addAll(((IteNode) statement.getStatement()).checkCall());
                }
            }
        }
        return call;
    }
    @Override
    public Environment checkEffects(Environment e) {
        e = exp.checkEffects(e);

        Environment e1 = e.clone();
        Environment e2 = e.clone();

        for(Node node : thenStatement) {
            StatementNode stmNode = (StatementNode) node;
            //controllo se c'è una chiamata ricorsiva
            if (stmNode.getStatement() instanceof CallNode cnode && stmNode.getFunNode().getId().getId().equals(cnode.getId())) {
                int count = 0;
                FunctionNode fnode = stmNode.getFunNode();
                Environment e_start;
                Environment e_end = e1.clone();
                for (int i = 0; i < cnode.getListId().size(); i++) {
                    STEntryAsset entryA = (STEntryAsset) Environment.lookup(e_end, cnode.getListId().get(i).getId());
                    entryA.setLiquidity(1);
                }
                do {
                    e_start = e_end.clone();
                    e_end = LiquidityUtils.fixPointMethod(e_start.clone(), fnode, cnode, false);
                    count++;
                    ArrayList<IdNode> formalParameter = fnode.getADec() != null ? fnode.getADec().getId() : new ArrayList<>();
                    for(int i = 0; i< formalParameter.size();i++){
                        //check that function has liquid
                        //=> all formal parameter are empty
                        STEntryAsset entryF = (STEntryAsset) Environment.lookup(e_end,formalParameter.get(i).getId());
                        boolean PassedToActual = false;
                        for (IdNode id: cnode.getListId()) {
                            if(Objects.equals(id.getId(), formalParameter.get(i).getId())){
                                PassedToActual = true;
                            }
                        };
                        if(entryF.getLiquidity() != 0 && !PassedToActual){
                            System.out.println("La funzione "+ fnode.getId().getId()+" non e' liquida! [liquidity]");
                            System.exit(0);
                        }
                    }
                } while(!(e_end.equals(e_start)) && count < 50);
                System.out.println("Iterator: "+count);
                e1 = e_end;
            }
            else {
                e1 = node.checkEffects(e1);
            }
        }

        if(elseStatement != null) {
            for (Node node : elseStatement) {
                StatementNode stmNode = (StatementNode) node;
                //controllo se c'è una chiamata ricorsiva
                if (stmNode.getStatement() instanceof CallNode cnode && stmNode.getFunNode().getId().getId().equals(cnode.getId())) {
                    int count = 0;
                    FunctionNode fnode = stmNode.getFunNode();
                    Environment e_start;
                    Environment e_end = e2.clone();
                    for (int i = 0; i < cnode.getListId().size(); i++) {
                        STEntryAsset entryA = (STEntryAsset) Environment.lookup(e_end, cnode.getListId().get(i).getId());
                        entryA.setLiquidity(1);
                    }
                    do {
                        e_start = e_end.clone();
                        e_end = LiquidityUtils.fixPointMethod(e_start.clone(), fnode, cnode, true);
                        count++;
                        ArrayList<IdNode> formalParameter = fnode.getADec() != null ? fnode.getADec().getId() : new ArrayList<>();
                        for(int i = 0; i< formalParameter.size();i++) {
                            //check that function has liquid
                            //=> all formal parameter are empty
                            STEntryAsset entryF = (STEntryAsset) Environment.lookup(e_end, formalParameter.get(i).getId());
                            boolean PassedToActual = false;
                            for (IdNode id : cnode.getListId()) {
                                if (Objects.equals(id.getId(), formalParameter.get(i).getId())) {
                                    PassedToActual = true;
                                }
                            }
                            ;
                            if (entryF.getLiquidity() != 0 && !PassedToActual) {
                                System.out.println("La funzione " + fnode.getId().getId() + " non e' liquida! [liquidity]");
                                System.exit(0);
                            }
                        }
                    } while (!(e_end.equals(e_start)) && count < 50);
                    System.out.println("Iterator: " + count);
                    e2 = e_end;
                } else {
                    e2 = node.checkEffects(e2);
                }
            }
        }
        return LiquidityUtils.max(e1,e2);
    }
}
