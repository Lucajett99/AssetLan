package utils;

import ast.Node;
import ast.TypeNode;

import java.util.ArrayList;

public class STentry {
    private Node type;

    private int offset;
    private int nestingLevel;
    private ArrayList<TypeNode> parameter;
    private int nArgs;
    private int nAssets;
    private boolean isFunction; //return true if symbol is a function , false is a variable
    private int liquidity;

    public STentry(Node type,int offset, int nestingLevel, ArrayList<TypeNode> parameter, int nArgs, int nAssets, boolean isFunction) {
        this.type = type;
        this.offset = offset;
        this.nestingLevel = nestingLevel;
        this.parameter = parameter;
        this.nArgs = nArgs;
        this.nAssets = nAssets;
        this.isFunction = isFunction;
    }

    public STentry(Node type, int offset, int nestingLevel){
        this.type = type;
        this.offset = offset;
        this.nestingLevel = nestingLevel;
        this.parameter = null;
        this.nArgs = 0;
        this.nAssets = 0;
        this.isFunction = false;
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

    public boolean isFunction() {
        return isFunction;
    }

    public void setFunction(boolean function) {
        this.isFunction = function;
    }

    public int getnAssets() {
        return nAssets;
    }

    public void setnAssets(int nAssets) {
        this.nAssets = nAssets;
    }

    public ArrayList<TypeNode> getParameter(){
        return parameter!=null?(parameter) : new ArrayList<>();
    }
    public Node getType() {
        return type;
    }

    public void addType(Node node){
        this.type = node;
    }
}
