package utils;

import ast.FunctionNode;
import ast.Node;
import ast.StatementNode;
import ast.TypeNode;

import java.util.ArrayList;
import java.util.HashMap;


public class Environment {
    private final ArrayList<HashMap<String, STentry>> symTable = new ArrayList<HashMap<String,STentry>>();
    private int nestingLevel;
    private int offset;

    public Environment() {
        this.nestingLevel = -1;
        this.offset = 0;
    }

    public ArrayList<HashMap<String, STentry>> getSymTable() {
        return symTable;
    }

    public int getOffset() {
        return this.offset;
    }

    public int setDecOffset() {
        this.offset--;
        return offset;
    }

    public int setFunOffset(){
        this.offset = this.offset - 2;
        return offset;
    }

    public int getNestingLevel() {
        return nestingLevel;
    }

    /*Return the hashMap of the external environment*/
    public HashMap<String,STentry> getHead(){
        return symTable.get(nestingLevel);
    }


    public void removeExternalEnv(){
        symTable.remove(nestingLevel);
        nestingLevel--;
    }

/*Verify is possible generate a multiple declaration error
* check only to head env contains the symbol */
    public EnvError isMultipleDeclared(String key){
        if(getHead().get(key) == null){
            return EnvError.NO_DECLARE;
        }else{
            return EnvError.ALREADY_DECLARED;
        }
    }

/*Given a key(String ) return if variable isn't declared(NO_DECLARE) or is declared(NONE)*/
    public EnvError isDeclared(String key){
        if (lookup(this,key) == null){
            return EnvError.NO_DECLARE;
        }else{
            return EnvError.DECLARED;
        }
    }

    /*Add a Symbol in a ST
    * the STentry type is made inside of this method
    * ---------------------------------------------
    * @return Environment with a new entry
    * */
    public static Environment addFunctionDeclaration(Environment env, int offset, String key, Node type, FunctionNode funNode){
        STentry entry = new STentry(type, offset, env.nestingLevel,funNode );
        HashMap<String,STentry> recentST = env.getHead();
        if(env.isMultipleDeclared(key) == EnvError.ALREADY_DECLARED){ return null; }    //MULTIPLE_DECLARATION
        else {
            entry.addType(type);
            recentST.put(key, entry); //env.getHead().put(key, entry);
            env.symTable.remove(env.nestingLevel);
            env.getSymTable().add(env.getNestingLevel(), recentST);
            return env;
        }
    }
    public static Environment addDeclaration(Environment env, int offset, String key, Node type) {
        STentry entry = new STentry(type, offset, env.nestingLevel);
        HashMap<String, STentry> recentST = env.getHead();
        if (env.isMultipleDeclared(key) == EnvError.ALREADY_DECLARED) {
            return null;
        }    //MULTIPLE_DECLARATION
        else {
            entry.addType(type);
            recentST.put(key, entry); //env.getHead().put(key, entry);
            env.symTable.remove(env.nestingLevel);
            env.getSymTable().add(env.getNestingLevel(), recentST);
            return env;
        }
    }

    public static Environment addDeclaration(Environment env, String key, int liquidity) {
        STentry entry = new STentry(null, 0, env.nestingLevel);
        entry.setLiquidity(liquidity);
        HashMap<String, STentry> recentST = env.getHead();
        if (env.isMultipleDeclared(key) == EnvError.ALREADY_DECLARED) {
            return null;
        }    //MULTIPLE_DECLARATION
        else {

            recentST.put(key, entry); //env.getHead().put(key, entry);
            env.symTable.remove(env.nestingLevel);
            env.getSymTable().add(env.getNestingLevel(), recentST);
            return env;
        }
    }

    public static STentry lookup(Environment env, String key){
        ArrayList<HashMap<String, STentry>> st = env.getSymTable();
        int stIndex = env.getNestingLevel();
        while(stIndex > -1){
            if (st.get(stIndex).get(key) != null) return st.get(stIndex).get(key);
            stIndex--;
        }
        //No Declaration Found
        return null;

    }

     /*When enter in a new environment must increase nesting level
     * and make a new Symbol Table for the last one*/
    public static Environment newScope(Environment env){
        env.nestingLevel++;
        env.offset = 0;
        HashMap<String, STentry> newHM = new HashMap<String, STentry>();
        env.getSymTable().add(newHM);
        return env;
    }

    /*Close the last environment, remove the last ST*/
    public static Environment exitScope(Environment env){
        env.removeExternalEnv();
        if(env.getNestingLevel() > -1){
            int minimumOffset = 0;
            HashMap<String,STentry> lastEnv = env.getSymTable().get(env.nestingLevel);
            for(String id : lastEnv.keySet())
                minimumOffset = lastEnv.get(id).getOffset() < minimumOffset ? lastEnv.get(id).getOffset() : minimumOffset;
        env.offset = minimumOffset;
        }
        return env;
    }

    /*liquidity control*/
    public static void setLiquidity(Environment e,String key,int liquidity){
        STentry entry = lookup(e,key);
        if(entry!=null){
            entry.setLiquidity(liquidity);
        }
    }

    public static boolean checkLiquidity(Environment e,String key,int liquidity){
        STentry entry = lookup(e,key);
        if(entry!=null){
            return entry.getLiquidity() == liquidity;
        }
        return false;
    }


}
