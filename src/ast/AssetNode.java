package ast;

import ast.Node;
import utils.EnvError;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class AssetNode implements Node {
    private IdNode id;

    public AssetNode(IdNode id) {
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        return indent+"Asset\n" + id.toPrint(indent + " ");
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
        if(e.isMultipleDeclared(id.getId()) == EnvError.NO_DECLARE)
            Environment.addDeclaration(e,id.getId(), this,null,0,false);
        else
            res.add(new SemanticError(id.getId()+" already declared [assetNode]"));
        return res;
    }
}
