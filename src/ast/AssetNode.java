package ast;

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
        return "subi $sp $sp 1\n";
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(e.checkHeadEnv(id.getId()) == EnvError.NO_DECLARE){ //controlla nell'ambiente di testa se é presente la dichiarazione
            int offset = e.setDecOffset(false);
            Environment.addDeclaration(e, offset, id.getId(), new AssetTypeNode());
            this.offset = offset;
        }
        else
            res.add(new SemanticError(id.getId()+" already declared [assetNode]"));
        return res;
    }

    @Override
    public Environment checkEffects(Environment e) {
        return Environment.addDeclaration(e,0,id.getId(),new AssetTypeNode());
    }

}
