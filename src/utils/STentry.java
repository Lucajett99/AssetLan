package utils;

import ast.Node;
import ast.TypeNode;

public class STentry {
    private Node type;
    private int nArguments;

    public STentry() { }

    public STentry(Node type) {
        this.type = type;
    }

    public Node getType() {
        return type;
    }

    public void addType(Node node){
        this.type = node;
    }
}
