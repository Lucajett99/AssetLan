package ast;

import ast.typeNode.AssetTypeNode;
import ast.typeNode.VoidTypeNode;
import utils.*;

import java.util.ArrayList;

public class MoveNode implements Node{

    private IdNode id1;
    private Node type1;

    private IdNode id2;
    private Node type2;

    public MoveNode(IdNode id1, IdNode id2) {
        this.id1 = id1;
        this.id2 = id2;
        this.type1= null;
        this.type2 = null;
    }


    @Override
    public String toPrint(String indent) {
        return indent+" transfer: "+id1.toPrint(indent)+" "+id2.toPrint(indent);
    }

    @Override
    public Node typeCheck() {
        if(!(Utilities.isSubtype(type1.typeCheck(),new AssetTypeNode()) &&
                Utilities.isSubtype(type2.typeCheck(),new AssetTypeNode()) )){
           System.out.println("Incompatible type error: Must Be AssetNode");
        };
        return new VoidTypeNode();
    }

    @Override
    public String codGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(e.isDeclared(id1.getId()) == EnvError.NO_DECLARE){
            res.add(new SemanticError((id1.getId())+": is not declared [Move]"));
        }

        STentry first = Environment.lookup(e,id1.getId());
        type1 = first.getType();

        if(e.isDeclared(id2.getId())== EnvError.NO_DECLARE){
            res.add(new SemanticError((id2.getId())+": is not declared [Move]"));
        }

        STentry second = Environment.lookup(e,id2.getId());
        type2 = second.getType();

        return res;
    }
}
