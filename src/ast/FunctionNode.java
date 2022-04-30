package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class FunctionNode implements Node {
    private TypeNode type;
    private IdNode id;
    private DecpNode decp;
    private ArrayList <DecNode> dec;
    private AdecNode adec;
    private ArrayList<StatementNode> statement;

    public FunctionNode(TypeNode type, IdNode id, DecpNode decp, ArrayList<DecNode> dec, AdecNode adec, ArrayList<StatementNode> statement) {
        this.type = type;
        this.decp = (decp);
        this.id = id;
        this.dec = dec;
        this.adec = adec;
        this.statement = statement;
    }

    @Override
    public String toPrint(String indent) {
        String str="";
        str += type.toPrint(indent + " ") + id.toPrint(indent + " ");
        if(!(decp ==null))
            str += decp.toPrint(indent + " ");
        if(adec != null)
            str += adec.toPrint(indent + " ");
        for (DecNode dec : dec)
                str += dec.toPrint(indent + "  ");
        for (Node statement : statement)
            str += statement.toPrint(indent+"  ");

        return indent+"Function\n" + str;
    }

    @Override
    public Node typeCheck() {
     for(Node node: statement){
         if(node instanceof ReturnNode){

         }
     }
        return null;
    }

    @Override
    public String codGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(env.isInsideFunction()){
           res.add(new SemanticError("isn't possibile declare a function [function]"));
           return res;
        }else{
            Environment e = Environment.addDeclaration(env, id.getId(), type); //Declaration of the function
            if(e == null) //if function is already declared
                res.add(new SemanticError(this.id.getId()+": id already declared [function]"));
            
            env = Environment.newScope(env);
            env.setInsideFunction(true);

            /*Aggiungo parametri formali contenuti in Decp*/
            if(decp!= null){
                res.addAll(decp.checkSemantics(env));
            }

            /*Aggiungo parametri formali Asset*/
            if(adec != null) {
                res.addAll(adec.checkSemantics(env));
            }

            /*Aggiungo dichiarazioni in Dec*/
            if(dec != null) {
                for (DecNode decNode : dec) {
                    res.addAll(decNode.checkSemantics(env));
                }
            }

            if(statement != null){
                for (Node st: statement ) {
                    res.addAll(st.checkSemantics(env));
                }
            }

            Environment.exitScope(env);
            env.setInsideFunction(false);
            return res;
        }
    }
}
