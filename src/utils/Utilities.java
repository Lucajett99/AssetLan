package utils;

import ast.IdNode;
import ast.Node;
import ast.TypeNode;
import ast.function.FunctionNode;
import ast.function.StatementNode;
import ast.statement.CallNode;
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

    private static int getLabelCounter() {
        return labelCounter;
    }

    private static void updateLabelCounter() {
        labelCounter++;
    }

    private static final int MAXITER = 100;

    public static String freshLabel(){
        String label = "label" + getLabelCounter();
        updateLabelCounter();
        return label;
    }

    public static Environment fixPointMethod(Environment e_start, FunctionNode funNode , CallNode callNode){
        ArrayList<StatementNode> stmList = funNode.getStatement(); //statement contenuti nella funzione
        String funName = funNode.getId().getId();

        Environment e_end;

        ArrayList<IdNode> actualParameter = callNode.getListId() != null ? callNode.getListId() : new ArrayList<>();
        ArrayList<IdNode> formalParameter = funNode.getADec() != null ? funNode.getADec().getId() : new ArrayList<>();
        int iteration = 0;
        do{ //TODO: fare controlli
            e_end = e_start.clone();
            iteration++;
            System.out.println("iter: "+iteration);
            for (int i = 0; i < actualParameter.size(); i++) {
                //aggiorno l'attuale in base al formale
                STentry entryA = Environment.lookup(e_end, actualParameter.get(i).getId());
                //STentry entryF = Environment.lookup(e,formalParameter.get(i).getId());
                STentry entryF = Environment.lookup(e_start, formalParameter.get(i).getId());
                entryF.setLiquidity(entryA.getLiquidity());
                //TODO: svuotare i parametri attuali in caso vengano gia passati alla func
            }

            for (StatementNode stm: stmList) {
                if(!(stm.getStatement() instanceof CallNode )){
                    e_start = stm.checkEffects(e_start);
                }
            }
        }while(!(e_start.equals(e_end)) && iteration <MAXITER );

        return e_end;

    }
}
