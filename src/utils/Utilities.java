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
                if (!(stm.getStatement() instanceof CallNode) && !(stm.getStatement() instanceof IteNode)) {
                    e_start = stm.checkEffects(e_start);
                } else if (stm.getStatement() instanceof IteNode) {
                    Environment e1 = e_start.clone();
                    Environment e2 = e_start.clone();

                    IteNode ite = (IteNode) stm.getStatement();
                    for (Node node : ite.getThenStatement()) {
                        StatementNode stmNode = (StatementNode) node;
                        if (!(stmNode.getStatement() instanceof CallNode) && !(stmNode.getStatement() instanceof IteNode))
                            e1 = node.checkEffects(e1);
                    }
                    if (ite.getElseStatement() != null) {
                        for (Node node : ite.getElseStatement()) {
                            StatementNode stmNode = (StatementNode) node;
                            if (!(stmNode.getStatement() instanceof CallNode) && !(stmNode.getStatement() instanceof IteNode)) {
                                e2 = stmNode.checkEffects(e2);
                            }
                        }
                    }
                    e_start = Environment.max(e1, e2);
                }

            }
        }while(!(e_start.equals(e_end)) && iteration <MAXITER );

    /*       for(int i = 0; i< formalParameter.size();i++){
                //check that function has liquid
                //=> all formal parameter are empty
                STentry entryF = Environment.lookup(e_start,formalParameter.get(i).getId());
                if(entryF.getLiquidity() != 0){
                    System.out.println("funzione "+funNode.getId().getId()+" non é liquida!");
                    System.exit(0);
                }
            }

    */

        return e_end;

    }
}
