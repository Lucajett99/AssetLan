package utils;

import ast.TypeNode;

import java.util.ArrayList;
import java.util.HashMap;


public class Environment {
    private final ArrayList<HashMap<String, STentry>> symTable = new ArrayList<HashMap<String,STentry>>();
    private int nestingLevel;
    private boolean insideFunct ;

    public boolean isInsideFunct() {
        return insideFunct;
    }

    public void setInsideFunct(boolean insideFunct) {
        this.insideFunct = insideFunct;
    }

    public Environment() {
        nestingLevel = -1;
        insideFunct = false;
    }

    public ArrayList<HashMap<String, STentry>> getSymTable() {
        return symTable;
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
            return EnvError.NONE;
        }else{
            return EnvError.ALREADY_DECLARED;
        }
    }

/*Given a key(String ) return if variable isn't declared(NO_DECLARE) or is declared(NONE)*/
    public EnvError isDeclared(String key){
        if (lookup(this,key) == null){
            return EnvError.NO_DECLARE;
        }else{
            return EnvError.NONE;
        }
    }
    /*Add a Symbol in a ST
    * the STentry type is made inside of this method
    * ---------------------------------------------
    * @return Environment with a new entry
    * */
    public static Environment addDeclaration(Environment env, String key, TypeNode type){
        STentry entry = new STentry(key,env.getNestingLevel(),type);
        HashMap<String,STentry> recentST = env.getHead();
        if(env.isMultipleDeclared(key)==EnvError.ALREADY_DECLARED){ return null;}    //MULTIPLE_DECLARATION
        else {
            recentST.put(key, entry);
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
        HashMap<String, STentry> newHM = new HashMap<String, STentry>();
        if(env.getSymTable().add(newHM)) return env;
        else return null;
    }

    /*Close the last environment, remove the last ST*/
    public static Environment exitScope(Environment env){
        env.removeExternalEnv();
        return env;
    }
}
