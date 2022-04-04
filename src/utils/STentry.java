package utils;

import ast.Node;

public class STentry {
    private String key;
    private int nestingLevel;
    private Node type;


    public STentry(String key, int nestingLevel, Node type ) {
        this.key = key;
        this.nestingLevel = nestingLevel;
        this.type = type;
    }

    public void addType(Node node){
        this.type = node;
    }
}
