package ast.statement;

import ast.IdNode;
import ast.Node;
import ast.typeNode.AssetTypeNode;
import utils.*;
import utils.StEntry.STEntryAsset;
import utils.StEntry.STentry;

import java.util.ArrayList;

public class MoveNode implements Node {

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
        if(!(type1.typeCheck().getClass().equals(new AssetTypeNode().getClass()) &&
                type2.typeCheck().getClass().equals(new AssetTypeNode().getClass()))){
           System.out.println("Incompatible type error: Must Be AssetNode");
           System.exit(0);
        }
        return null;
    }

    @Override
    public String codGeneration() {
        String moveCode =  id1.accessCodGeneration() //I have the value of id1 in $a0
                         + id1.emptyValueCodGeneration() //the value of id1 is saved in $a0 I empty the register
                         + "push $a0 \n"
                         + id2.accessCodGeneration() //I have the value of id2 in $a0
                         + "lw $a1 0($sp)\n" //a1 <- top
                         + "pop\n"
                         + "add $a0 $a0 $a1\n"  //sum the value of id1 and id2
                         + id2.storeCodGeneration();  //store in id2 the sum of id1 and id2
        return moveCode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        res.addAll(id1.checkSemantics(e));
        res.addAll(id2.checkSemantics(e));

        STentry first = Environment.lookup(e,id1.getId());
        if(first != null)
            type1 = first.getType();

        STentry second = Environment.lookup(e,id2.getId());
        if(second != null)
            type2 = second.getType();

        return res;
    }

    @Override
    public Environment checkEffects(Environment e) {

        STEntryAsset entry1 = (STEntryAsset)Environment.lookup(e,id1.getId()); // /gamma'(x)
        STEntryAsset entry2 = (STEntryAsset)Environment.lookup(e,id2.getId()); // /gamma'(x)
        int liq1 = entry1.getLiquidity();
        int liq2 = entry2.getLiquidity();//  /gamma'(y)
        entry1.setLiquidity(0);         //  /gamma"[x -> 0 , y -> \gamma'(x) (+) \gamma'(y)]
        entry2.setLiquidity(Math.max(liq1,liq2));
        return e;
    }
}
