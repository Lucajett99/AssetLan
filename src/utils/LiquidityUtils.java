package utils;

import ast.IdNode;
import ast.Node;
import ast.function.FunctionNode;
import ast.function.StatementNode;
import ast.statement.CallNode;
import ast.statement.IteNode;
import utils.StEntry.STEntryAsset;
import utils.StEntry.STEntryFun;
import utils.StEntry.STentry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static utils.Environment.lookup;

public abstract class LiquidityUtils {

    private static final int MAXITER = 100;
    public static Environment max(Environment e1,Environment e2){
        ArrayList<HashMap<String, STentry>> stNew = new ArrayList<HashMap<String,STentry>>();
        ArrayList<HashMap<String,STentry>> st = e1.getSymTable();

        for(int i = 0; i< st.size();i++){
            HashMap<String, STentry> newHM =new HashMap<String, STentry>();
            for(String id : st.get(i).keySet()){
                STEntryAsset entry1 = null;
                STEntryAsset entry2 = null;

                STentry entry = e2.getSymTable().get(i).get(id);
                if(entry instanceof STEntryAsset){entry2 = (STEntryAsset) entry; }
                entry = e1.getSymTable().get(i).get(id);
                if(entry instanceof STEntryAsset){entry1 = (STEntryAsset) entry; }
                if( entry2 != null && entry1 != null){
                    entry2.setLiquidity(Math.max(entry2.getLiquidity(),entry1.getLiquidity()));
                    newHM.put(id,entry2);
                }else{
                    newHM.put(id,entry);
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
                if(entry instanceof STEntryAsset){
                    if(((STEntryAsset) entry).getLiquidity() != 0)counter++;
                }
            }
        }
        return counter;
    }

    /* fixPointMethod : prende in input @e_start => ambiente iniziale
     *                                   @funNode => StEntry della funzione contenente gli statement e la lista dei parametri formali
     *                                   @callNode=> contiene l'ordine e il nome dei parametri attuali*/
    public static Environment fixPointMethod(Environment e_start, FunctionNode funNode , CallNode callNode, boolean branchElse) {
        ArrayList<StatementNode> stmList = funNode.getStatement(); //Corpo della funzione
        boolean fixPoint = true;

        Environment e_end;
        //prende la lista dei parametri attuali e formali
        ArrayList<IdNode> actualParameter = callNode.getListId() != null ? callNode.getListId() : new ArrayList<>();
        ArrayList<IdNode> formalParameter = funNode.getADec() != null ? funNode.getADec().getId() : new ArrayList<>();

        e_end = e_start.clone();
        for (int i = 0; i < actualParameter.size(); i++) {
            IdNode id = formalParameter.get(i);
            //aggiorno l'attuale in base al formale
            STEntryAsset entryA = (STEntryAsset) Environment.lookup(e_start, actualParameter.get(i).getId());
            STEntryAsset entryF = (STEntryAsset) Environment.lookup(e_end, formalParameter.get(i).getId());
            //STentry entryA2 = Environment.lookup(e_end, actualParameter.get(i).getId());

            if(entryA.getLiquidity() > 0) fixPoint = false;  //se i parametri che passo sono entrambi con liquidity 0, ho trovato il minimo punto fisso
            entryF.setLiquidity(entryA.getLiquidity());
            //entryA2.setLiquidity(0); //setto la liquidity a 0 dei parametri passati
        }

        if(fixPoint) return e_start;

        for (StatementNode stm: stmList) {
            if (!(stm.getStatement() instanceof CallNode && ((CallNode) stm.getStatement()).getId() == callNode.getId()) && !(stm.getStatement() instanceof IteNode)) {
                e_end = stm.checkEffects(e_end);
            }
            else if (stm.getStatement() instanceof IteNode ite) {
                if(!branchElse) {
                    for (Node node : ite.getThenStatement()) {
                        if(!(((StatementNode) node).getStatement() instanceof  IteNode) && !(((StatementNode) node).getStatement() instanceof CallNode cnode && cnode.getId() == callNode.getId()))
                            e_end = node.checkEffects(e_end);
                    }
                }
                else {
                    if(ite.getElseStatement() != null)
                        for (Node node : ite.getElseStatement()) {
                            if(!(((StatementNode) node).getStatement() instanceof  IteNode) && !(((StatementNode) node).getStatement() instanceof CallNode cnode && cnode.getId() == callNode.getId()))
                                e_end = node.checkEffects(e_end);
                        }
                }
            }
        }
        return e_end;
    }
}
