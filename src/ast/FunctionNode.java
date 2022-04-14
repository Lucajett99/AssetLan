package ast;

import utils.EnvError;
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
        return null;
    }

    @Override
    public String codGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(env.isInsideFunct()){
           res.add(new SemanticError("isn't possibile declared a function [function]"));
           return res;
        }else{
            Environment e = Environment.addDeclaration(env, id.getId(), type);
            if(e == null){ //if function is already declared
                res.add(new SemanticError(this.id.getId()+": function already declared [function]"));
                return res;
            }else{
                env = Environment.newScope(env);
                env.setInsideFunct(true);

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
                env.setInsideFunct(false);
                return res;
            }


        }
    }
}
