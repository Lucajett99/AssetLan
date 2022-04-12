package utils;

import ast.Node;
import ast.TypeNode;

public class STentry {
    private String key;
    private int nestingLevel;
    private TypeNode type;
    private int nArguments;

    public String getKey() {
        return key;
    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    public Node getType() {
        return type;
    }

    public STentry(String key, int nestingLevel, TypeNode type) {
        this.key = key;
        this.nestingLevel = nestingLevel;
        this.type = type;
    }

    public void addType(TypeNode node){
        this.type = node;
    }
}
