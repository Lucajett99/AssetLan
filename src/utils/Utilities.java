package utils;

import ast.IdNode;
import ast.Node;
import ast.TypeNode;
import ast.function.FunctionNode;
import ast.function.StatementNode;
import ast.statement.CallNode;
import ast.statement.IteNode;
import ast.typeNode.AssetTypeNode;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Utilities {
    private static int labelCounter = 0;
    public static boolean isSubtype (Node a, Node b) {
        return a.getClass().isAssignableFrom( b.getClass()); // ||
        // ( (a instanceof BoolTypeNode) && (b instanceof IntTypeNode) ); //
    }

    private static int getLabelCounter() {
        return labelCounter;
    }

    private static void updateLabelCounter() {
        labelCounter++;
    }

    public static String freshLabel(){
        String label = "label" + getLabelCounter();
        updateLabelCounter();
        return label;
    }

    public static void nCallRecursive(String idFun, ArrayList<StatementNode> stmList) {
        int nCall = 0;

        for(StatementNode stm : stmList) {
            if(stm.getStatement() instanceof CallNode callNode && callNode.getId().equals(idFun))
                nCall++;
        }

        if(nCall > 1) {
            System.out.println("Il programma non e' liquido!");
            System.exit(0);
        }
    }
}
