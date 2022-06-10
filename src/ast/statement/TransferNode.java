package ast.statement;

import ast.IdNode;
import ast.Node;
import ast.typeNode.AssetTypeNode;
import ast.typeNode.VoidTypeNode;
import utils.*;
import utils.StEntry.STEntryAsset;
import utils.StEntry.STentry;

import java.util.ArrayList;

public class TransferNode implements Node {
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
        return null;
    }

    @Override

    public String codGeneration() {
        // I suppose to have a register $b (balance) in which i will store the aggregate sum of all transfers in the program
        String transferCode = id.accessCodGeneration()
                            + id.emptyValueCodGeneration()
                            + "add $b $b $a0\n";
        return transferCode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();

        /*if(e.isDeclared(id.getId())== EnvError.NO_DECLARE){
                res.add(new SemanticError((id.getId())+": is not declared [transfer]"));
        }
        type = Environment.lookup(e,id.getId()).getType();*/
        res.addAll(id.checkSemantics(e)); //Check if id is declared or not
        type = Environment.lookup(e,id.getId()).getType();
        return res;
    }
    @Override
    public Environment checkEffects(Environment e) {
        STentry st =Environment.lookup(e,id.getId());
        if(st instanceof STEntryAsset){
            ((STEntryAsset) st).setLiquidity(0);}
        return e;
    }
}
