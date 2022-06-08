package utils;

import ast.Node;
import ast.function.FunctionNode;

import java.util.Objects;

public class STEntryFun implements STentry{
    private Node type;
    private int nestingLevel;
    private int offset;
    private FunctionNode node;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public Node getType() {
        return type;
    }

    public void setType(Node type) {
        this.type = type;
    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    public void setNestingLevel(int nestingLevel) {
        this.nestingLevel = nestingLevel;
    }

    public FunctionNode getNode() {
        return node;
    }

    public void setNode(FunctionNode node) {
        this.node = node;
    }

    public STEntryFun(Node type,int offset, int nestingLevel, FunctionNode node) {
        this.type = type;
        this.offset = offset;
        this.nestingLevel = nestingLevel;
        this.node = node;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        STEntryFun that = (STEntryFun) o;
        return nestingLevel == that.nestingLevel && offset == that.offset && Objects.equals(type, that.type) && Objects.equals(node, that.node);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, nestingLevel, offset, node);
    }
}
