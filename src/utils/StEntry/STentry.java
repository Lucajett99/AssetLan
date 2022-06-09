package utils.StEntry;

import ast.function.FunctionNode;
import ast.Node;

public interface STentry {
    public int getOffset();
    public int getNestingLevel();
    public Node getType();

    public boolean equals(Object o);


}
