package utils;

import ast.AssetNode;
import ast.function.FunctionNode;
import ast.Node;
import ast.typeNode.AssetTypeNode;
import org.stringtemplate.v4.ST;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class Environment extends LiquidityUtils{
    private final ArrayList<HashMap<String, STentry>> symTable;
    private int nestingLevel;
    private int offset;

    public Environment(ArrayList<HashMap<String,STentry>> st, int nestingLevel, int offset) {
        this.symTable = st;
        this.nestingLevel = nestingLevel;
        this.offset = offset;
    }

    public Environment() {
        this.symTable=  new ArrayList<HashMap<String,STentry>>();
        this.nestingLevel = -1;
        this.offset = 0;
    }

    public ArrayList<HashMap<String, STentry>> getSymTable() {
        return symTable;
    }

    public int getOffset() {
        return this.offset;
    }

    public Environment clone(){
        ArrayList<HashMap<String, STentry>> newST = new ArrayList<HashMap<String,STentry>>();
        for(int i =0; i< symTable.size();i++){
            HashMap<String,STentry> HM = new HashMap<String,STentry>();
            for(String id : symTable.get(i).keySet()) {
                STentry entry = symTable.get(i).get(id);
                if (entry instanceof STEntryAsset){
                    HM.put(id, new STEntryAsset(entry.getType(), entry.getOffset(), entry.getNestingLevel(),((STEntryAsset) entry).getLiquidity()));
                }else if(entry instanceof STEntryVar){
                    HM.put(id, new STEntryVar(entry.getType(), entry.getOffset(), entry.getNestingLevel()));
                }else if(entry instanceof STEntryFun){
                    HM.put(id, new STEntryFun(entry.getType(), entry.getOffset(), entry.getNestingLevel(), ((STEntryFun) entry).getNode()));
                }
            }
            newST.add(HM);
        }
        return new Environment(newST,nestingLevel,offset);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Environment that = (Environment) o;

        for (int i = 0;i< symTable.size();i++) {
            for(String id : symTable.get(i).keySet()){
                STentry entry1 = that.symTable.get(i).get(id);
                STentry entry2 = symTable.get(i).get(id);
                if(!entry1.equals(entry2))return false;
            }
        }
        return nestingLevel == that.nestingLevel && offset == that.offset;
    }

    public int setDecOffset(boolean isInsideFunction) {
         if(isInsideFunction)
             this.offset++;
         else
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
        STEntryFun entry = new STEntryFun(type, offset, env.nestingLevel,funNode );
        HashMap<String,STentry> recentST = env.getHead();
        if(env.isMultipleDeclared(key) == EnvError.ALREADY_DECLARED){ return null; }    //MULTIPLE_DECLARATION
        else {
            entry.setType(type);
            recentST.put(key, entry); //env.getHead().put(key, entry);
            env.symTable.remove(env.nestingLevel);
            env.getSymTable().add(env.getNestingLevel(), recentST);
            return env;
        }
    }
    public static Environment addDeclaration(Environment env, int offset, String key, Node type) {
        STEntryVar entry;
        if(type instanceof AssetTypeNode){
            entry = new STEntryAsset(type,offset, env.nestingLevel, 0);
        }else{
            entry = new STEntryVar(type,offset,env.nestingLevel);
        }
        HashMap<String, STentry> recentST = env.getHead();
        if (env.isMultipleDeclared(key) == EnvError.ALREADY_DECLARED) {
            return null;
        }    //MULTIPLE_DECLARATION
        else {
            entry.setType(type);
            recentST.put(key, entry); //env.getHead().put(key, entry);
            env.symTable.remove(env.nestingLevel);
            env.getSymTable().add(env.getNestingLevel(), recentST);
            return env;
        }
    }

    public static Environment addDeclaration(Environment env, String key, int liquidity) {
        STEntryAsset entry = new STEntryAsset(null, 0, env.nestingLevel,liquidity);
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
            if(lastEnv.isEmpty()) {
                for (String id : lastEnv.keySet())
                    minimumOffset = Math.min(lastEnv.get(id).getOffset(), minimumOffset);
            }
            env.offset = minimumOffset;

        }
        return env;
    }


}
