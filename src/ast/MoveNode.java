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
        return new AssetTypeNode();
    }

    @Override
    public String codGeneration() {
        String moveCode =  id1.codGeneration()
                         + id1.emptyValueCodGeneration() //the value of id1 is saved in $a0 I empty the register
                         + "push $a0 \n"
                         + id2.codGeneration() //now $al is setted to the value of
                         + "lw $t 0($sp) \n"
                         + "add $a0 $a0 $t \n" //now $a0 contains the sum of id1 and id2, I have to load id2
                         + id2.updateValueCodGeneration();
        return moveCode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(e.isDeclared(id1.getId()) == EnvError.NO_DECLARE){
            res.add(new SemanticError((id1.getId())+": is not declared [Move]"));
        }
        else {
            STentry first = Environment.lookup(e,id1.getId());
            type1 = first.getType();
        }

        if(e.isDeclared(id2.getId())== EnvError.NO_DECLARE){
            res.add(new SemanticError((id2.getId())+": is not declared [Move]"));
        }
        else {
            STentry second = Environment.lookup(e,id2.getId());
            type2 = second.getType();
        }

        return res;
    }

    @Override
    public Environment checkEffects(Environment e) {
        Environment e1 = e;                 //passaggio per riferimento?
        int liq1 = Environment.lookup(e,id1.getId()).getLiquidity(); // /gamma'(x)
        int liq2 = Environment.lookup(e,id2.getId()).getLiquidity();//  /gamma'(y)
        Environment.lookup(e1,id1.getId()).setLiquidity(0);         //  /gamma"[x -> 0 , y -> \gamma'(x) (+) \gamma'(y)]
        Environment.lookup(e1,id2.getId()).setLiquidity(Math.max(liq1,liq2));
        return e1;
    }
}
