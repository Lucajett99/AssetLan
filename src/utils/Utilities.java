package utils;

import ast.Node;
import ast.TypeNode;

import java.util.Objects;

public class Utilities {
    public static boolean isSubtype (Node a, Node b) {
        return a.getClass().equals(b.getClass()) ; //||
        // ( (a instanceof BoolTypeNode) && (b instanceof IntTypeNode) ); //
    }
}
