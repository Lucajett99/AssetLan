package utils;

import ast.function.FunctionNode;
import ast.Node;

public class STentry {
    private Node type;

    private int offset;
    private int nestingLevel;
    private FunctionNode node;
    private int liquidity;

    public STentry(Node type,int offset, int nestingLevel,FunctionNode node) {
        this.type = type;
        this.offset = offset;
        this.nestingLevel = nestingLevel;
        this.node = node;
        this.liquidity = -1;
    }

    public STentry(Node type, int offset, int nestingLevel){
        this.type = type;
        this.offset = offset;
        this.nestingLevel = nestingLevel;
        this.node = null;
        this.liquidity = 0;
    }
    public STentry(int liquidity){
        this.type = type;
        this.offset = offset;
        this.nestingLevel = nestingLevel;
        this.liquidity = liquidity;
    }

    public FunctionNode getNode() {
        return node;
    }

    public void setNode(FunctionNode node) {
        this.node = node;
    }

    public int getOffset() {
        return offset;
    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    public int getLiquidity() {
        return liquidity;
    }

    public void setLiquidity(int liquidity) {
        this.liquidity = liquidity;
    }


    public Node getType() {
        return type;
    }

    public void addType(Node node){
        this.type = node;
    }
}
