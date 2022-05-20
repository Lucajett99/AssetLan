package utils;

import ast.Node;
import ast.TypeNode;
import ast.typeNode.AssetTypeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Utilities {
    private static int labelCounter = 0;
    public static boolean isSubtype (Node a, Node b) {
        return a.getClass().isAssignableFrom( b.getClass()); // ||
        // ( (a instanceof BoolTypeNode) && (b instanceof IntTypeNode) ); //
    }

    public static int getLabelCounter() {
        return labelCounter;
    }

    public static void updateLabelCounter() {
        labelCounter++;
    }

    public static String freshLabel(){
        String label = "label" + getLabelCounter();
        updateLabelCounter();
        return label;
    }

}
