package ast;

import ast.Node;
import ast.typeNode.AssetTypeNode;
import utils.EnvError;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class AssetNode implements Node {
    private IdNode id;
    private int offset;


    public AssetNode(IdNode id) {
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        return indent+"Asset\n" + id.toPrint(indent + " ");
    }

    @Override
    public Node typeCheck() {
        return new AssetTypeNode();
    }

    @Override
    public String codGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(e.isMultipleDeclared(id.getId()) == EnvError.NO_DECLARE){
            int offset = e.setDecOffset();
            Environment.addDeclaration(e, offset, id.getId(), this);
            this.offset = offset;
        }
        else
            res.add(new SemanticError(id.getId()+" already declared [assetNode]"));
        return res;
    }

    @Override
    public ArrayList<String> checkEffects(Environment e) {
        ArrayList<String> res = new ArrayList<>();
        if(e.isMultipleDeclared(id.getId()) == EnvError.NO_DECLARE) {
            Environment.addDeclaration(e, e.setDecOffset(), id.getId(), this);
            Environment.lookup(e,id.getId()).setLiquidity(1);
        }else
            res.add(id.getId()+" already declared [assetNode]");

        return res;
    }
}
