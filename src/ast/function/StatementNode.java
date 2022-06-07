package ast.function;

import ast.Node;
import ast.statement.IteNode;
import utils.Environment;
import utils.SemanticError;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

public class StatementNode implements Node {
    private Node statement;
    private FunctionNode funNode; //save FN of the ancestor

    public FunctionNode getFunNode() {
        return funNode;
    }

    public void setFunNode(FunctionNode funNode) {
        this.funNode = funNode;
        if(statement instanceof IteNode ite){
            for(Node node: ite.getThenStatement()){
                StatementNode stm = (StatementNode) node;
                stm.setFunNode(funNode);
            }
            if(ite.getElseStatement()!= null){
                for(Node node: ite.getElseStatement()){
                    StatementNode stm = (StatementNode) node;
                    stm.setFunNode(funNode);
                }
            }
        }

    }

    public StatementNode(Node statement) {
        this.statement = statement;
        funNode =null;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "Statement\n" + statement.toPrint(indent + " ");
    }

    @Override
    public Node typeCheck() {
        return statement.typeCheck();
    }

    @Override
    public String codGeneration() {
        return statement.codGeneration();
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(statement!= null){
            res.addAll(statement.checkSemantics(e));
        }
        return res;
    }

    @Override
    public Environment checkEffects(Environment e) {
        return statement.checkEffects(e);
    }

    public Node getStatement() {
        return statement;
    }
}
