package utils;

import ast.Node;
import ast.TypeNode;

import java.util.ArrayList;

public class STentry {
    private Node type;
    private int nestingLevel;
    private ArrayList<TypeNode> parameter;
    private int nArgs;
    private int nAssets;
    private boolean function; //return true if symbol is a function , false is a variable

    public STentry(Node type, int nestingLevel, ArrayList<TypeNode> parameter, int nArgs, int nAssets, boolean function) {
        this.type = type;
        this.nestingLevel = nestingLevel;
        this.parameter = parameter;
        this.nArgs = nArgs;
        this.nAssets = nAssets;
        this.function = function;
    }

    public boolean isFunction() {
        return function;
    }

    public void setFunction(boolean function) {
        this.function = function;
    }

    public int getnAssets() {
        return nAssets;
    }

    public void setnAssets(int nAssets) {
        this.nAssets = nAssets;
    }

    public ArrayList<TypeNode> getParameter(){
        return parameter;
    }
    public Node getType() {
        return type;
    }

    public void addType(Node node){
        this.type = node;
    }
}
