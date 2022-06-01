package utils;

import ast.IdNode;
import ast.Node;
import ast.function.FunctionNode;
import ast.function.StatementNode;
import ast.statement.CallNode;
import ast.statement.IteNode;
import ast.typeNode.AssetTypeNode;

import java.util.ArrayList;
import java.util.HashMap;

import static utils.Environment.lookup;

public abstract class LiquidityUtils {

    private static final int MAXITER = 100;
    public static Environment max(Environment e1,Environment e2){
        ArrayList<HashMap<String,STentry>> stNew = new ArrayList<HashMap<String,STentry>>();
        ArrayList<HashMap<String,STentry>> st = e1.getSymTable();

        for(int i = 0; i< st.size();i++){
            HashMap<String, STentry> newHM =new HashMap<String, STentry>();
            for(String id : st.get(i).keySet()){
                STentry entry2 = lookup(e2,id);
                STentry entry1 = lookup(e1,id);
                if( lookup(e2,id) != null && entry1.getLiquidity()!= -1){
                    newHM.put(id,entry2);
                    newHM.get(id).setLiquidity(Math.max(entry2.getLiquidity(),entry1.getLiquidity()));
                }else{
                    newHM.put(id,entry1);
                }
            }
            stNew.add(newHM);
        }
        return new Environment(stNew, e1.getNestingLevel(), e1.getOffset());//to call in IteNote.checkEffects()
    }

    public static int checkGlobalLiquidity(Environment e){
        //return number of the global asset that are not empty
        ArrayList<HashMap<String,STentry>> nestingEnv = e.getSymTable();
        int counter = 0;
        for(HashMap<String,STentry> env : nestingEnv){
            //cicla solo una volta all'interno del for
            //per completezza cicla su tutti gli ambienti annidati
            // per definizione pero si potrebbe chiamare solo il primo ambiente dell'array list
            for(String id : env.keySet()){
                // controlla per ogni chiave appartenente all'ambiente
                //se la STEntry contiene un identificatore di tipo asset
                STentry entry = lookup(e,id);
                if(Utilities.isSubtype(entry.getType(),new AssetTypeNode())){
                    if(entry.getLiquidity() != 0)counter++;
                }
            }
        }
        return counter;
    }

    /* fixPointMethod : prende in input @e_start => ambiente iniziale
     *                                   @funNode => StEntry della funzione contenente gli statement e la lista dei parametri formali
     *                                   @callNode=> contiene l'ordine e il nome dei parametri attuali*/
    public static Environment fixPointMethod(Environment e_start, FunctionNode funNode , CallNode callNode){
        ArrayList<StatementNode> stmList = funNode.getStatement(); //Corpo della funzione
        String funName = funNode.getId().getId();

        Environment e_end;
        //prende la lista dei parametri attuali e formali
        ArrayList<IdNode> actualParameter = callNode.getListId() != null ? callNode.getListId() : new ArrayList<>();
        ArrayList<IdNode> formalParameter = funNode.getADec() != null ? funNode.getADec().getId() : new ArrayList<>();

        int iteration = 0;

        /*for(int i = 0; i< formalParameter.size();i++) {
            //check that function has liquid
            //=> all formal parameter are empty
            STentry entryF = Environment.lookup(e_start, formalParameter.get(i).getId());
            if (entryF.getLiquidity() > 0) {
                System.out.println("funzione " + callNode.getId() + " non é liquida!");
                //System.exit(0);
            }
        }*/
        do{
            e_end = e_start.clone();
            iteration++;
            System.out.println("iter: "+iteration);
            for (int i = 0; i < actualParameter.size(); i++) {
                //aggiorno l'attuale in base al formale
                STentry entryA = Environment.lookup(e_end, actualParameter.get(i).getId());
                //STentry entryF = Environment.lookup(e,formalParameter.get(i).getId());
                STentry entryF = Environment.lookup(e_start, formalParameter.get(i).getId());
                entryF.setLiquidity(entryA.getLiquidity());
            }

            for (StatementNode stm: stmList) {//esegue l'analisi sul corpo della funzione in maniera iterativa
                //da porre particolare attenzione in caso sia contenuto uno stm ITE
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

        for(int i = 0; i< formalParameter.size();i++){
            //check that function has liquid
            //=> all formal parameter are empty
            STentry entryF = Environment.lookup(e_end,formalParameter.get(i).getId());
            if(entryF.getLiquidity() != 0){
                System.out.println("funzione "+funNode.getId()+" non é liquida!");
                //System.exit(0);
            }
        }
        return e_end;

    }
}