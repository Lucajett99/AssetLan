package ast;

import utils.EnvError;
import utils.Environment;
import utils.SemanticError;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TransferNode implements Node{
    private IdNode id;

    public TransferNode(IdNode id) {
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "transfer\n"+id.toPrint(indent);
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
        if(e.isDeclared(id.getId())== EnvError.NO_DECLARE){
                res.add(new SemanticError((id.getId())+": is not declared [transfer]"));
        }
        return res;
    }
}
