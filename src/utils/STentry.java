package utils;

import ast.Node;
import ast.TypeNode;

public class STentry {
    private String key;
    private int nestingLevel;
    private String type;
    private int nArguments;
    public STentry(String key, int nestingLevel, String type) {
        this.key = key;
        this.nestingLevel = nestingLevel;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void addType(String node){
        this.type = node;
    }
}
