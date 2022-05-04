package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class StatementNode implements Node {
    private Node statement;

    public StatementNode(Node statement) {
        this.statement = statement;
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
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(statement!= null){
            res.addAll(statement.checkSemantics(e));
        }
        return res;
    }

    public Node getStatement() {
        return statement;
    }
}
