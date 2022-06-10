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
    public static boolean fixPoint = false;
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
    public static Environment startFixPointMethod(Environment e_start, FunctionNode funNode, CallNode callNode) {
        ArrayList<StatementNode> stmList = funNode.getStatement(); //Corpo della funzione
        boolean fixPoint = true; //mi serve per fermarmi se trovo il minimo punto fisso

        Environment e_end;
        //prende la lista dei parametri attuali e formali
        ArrayList<IdNode> actualParameter = callNode.getListId() != null ? callNode.getListId() : new ArrayList<>();
        ArrayList<IdNode> formalParameter = funNode.getADec() != null ? funNode.getADec().getId() : new ArrayList<>();

        e_end = e_start.clone();
        for (int i = 0; i < actualParameter.size(); i++) {
            //aggiorno l'attuale in base al formale (al primo giro saranno tutti 1)
            STEntryAsset entryA = (STEntryAsset) Environment.lookup(e_start, actualParameter.get(i).getId());
            STEntryAsset entryF = (STEntryAsset) Environment.lookup(e_end, formalParameter.get(i).getId());

            if(entryA.getLiquidity() > 0) fixPoint = false;  //se i parametri che passo sono entrambi con liquidity 0, ho trovato il minimo punto fisso
            entryF.setLiquidity(entryA.getLiquidity());
        }

        if(fixPoint) return e_start; //se ho trovato il minimo punto fisso ho finito (es: f()[0,0])

        //scorro la lista degli statement del corpo della funzione
        for (StatementNode stm: stmList) {
            //se non è una chiamata ricorsiva e non è un IteNode allora faccio la checkEffects dello statement
            if (!(stm.getStatement() instanceof CallNode && ((CallNode) stm.getStatement()).getId().equals(callNode.getId())) && !(stm.getStatement() instanceof IteNode)) {
                e_end = stm.checkEffects(e_end);
            }
            //se invece lo statement è un IteNode, allora vado a scorrere gli statement del then o dell'else in base a quale mi serve controllare
            else if (stm.getStatement() instanceof IteNode ite) {
                Environment e1 = e_end.clone();
                Environment e2 = e_end.clone();
                for (Node node : ite.getThenStatement()) {
                    if(!(((StatementNode) node).getStatement() instanceof CallNode cnode && cnode.getId().equals(callNode.getId())))
                        e1 = node.checkEffects(e1);
                }
                if(ite.getElseStatement() != null)
                    for (Node node : ite.getElseStatement()) {
                        if(!(((StatementNode) node).getStatement() instanceof CallNode cnode && cnode.getId().equals(callNode.getId())))
                            e2 = node.checkEffects(e2);
                    }
                e_end = LiquidityUtils.max(e1,e2);
            }
        }
        return e_end;
    }

    public static Environment fixPointMethod(Environment e, ArrayList<IdNode> actualParams, FunctionNode funNode, CallNode callNode, boolean... branchElse) {
        Environment e_start;
        Environment e_end = e.clone();
        int count = 0;
        for (int i = 0; i < actualParams.size(); i++) {
            //quando parto con il punto fisso, setto parametri attuali a 1
            STEntryAsset entryA = (STEntryAsset) lookup(e_end, actualParams.get(i).getId());
            entryA.setLiquidity(1);
        }
        LiquidityUtils.fixPoint = true;
        do {
            e_start = e_end.clone();
            e_end = LiquidityUtils.startFixPointMethod(e_start.clone(), funNode, callNode);
            count++;
            ArrayList<IdNode> formalParameter = funNode.getADec() != null ? funNode.getADec().getId() : new ArrayList<>();
            for(int i = 0; i< formalParameter.size();i++){
                //check that function has liquid
                //=> all formal parameter are empty
                STEntryAsset entryF = (STEntryAsset) lookup(e_end,formalParameter.get(i).getId());
                boolean PassedToActual = false;
                for (IdNode id: actualParams) {
                    if(Objects.equals(id.getId(), formalParameter.get(i).getId())){
                        PassedToActual = true;
                    }
                };
                if(entryF.getLiquidity() != 0 && !PassedToActual){
                    System.out.println("La funzione "+ funNode.getId().getId()+" non e' liquida! [liquidity]");
                    System.exit(0);
                }
            }
        } while(!(e_end.equals(e_start)) && count < MAXITER);
        return e_end;
    }
}
