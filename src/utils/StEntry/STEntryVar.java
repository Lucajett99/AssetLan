package utils.StEntry;

import ast.Node;

import java.util.Objects;

public class STEntryVar implements STentry {
    private Node type;
    private int offset;
    private int nestingLevel;

    public Node getType() {
        return type;
    }

    public void setType(Node type) {
        this.type = type;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    public void setNestingLevel(int nestingLevel) {
        this.nestingLevel = nestingLevel;
    }

    public STEntryVar(Node type, int offset, int nestingLevel) {
        this.type = type;
        this.offset = offset;
        this.nestingLevel = nestingLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        STEntryVar that = (STEntryVar) o;
        return offset == that.offset && nestingLevel == that.nestingLevel && Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, offset, nestingLevel);
    }
}
