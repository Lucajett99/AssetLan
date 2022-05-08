package ast;

import ast.typeNode.AssetTypeNode;
import ast.typeNode.VoidTypeNode;
import utils.*;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TransferNode implements Node{
    private IdNode id;
    private Node type;

    public TransferNode(IdNode id) {
        this.id = id;
        this.type = null;
    }

    @Override
    public String toPrint(String indent) {
        return indent + "transfer\n"+id.toPrint(indent);
    }

    @Override
    public Node typeCheck() {
        if(type != null){
            if(!Utilities.isSubtype(type.typeCheck(), new AssetTypeNode())){
                System.out.println("Incompatible Type System : Transfer must be Asset");
            }
        }
        return new VoidTypeNode();
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
        type = Environment.lookup(e,id.getId()).getType();

        return res;
    }

    @Override
    public ArrayList<String> checkEffects(Environment e) {
        ArrayList<String> res = new ArrayList<String>();
        if(e.isDeclared(id.getId())== EnvError.NO_DECLARE){
            res.add(id.getId()+": is not declared [transfer]");
        }
        Environment.lookup(e,id.getId()).setLiquidity(0);
        return res;
    }
}
