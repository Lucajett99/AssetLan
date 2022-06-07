package ast.function;

import ast.*;
import ast.statement.IteNode;
import ast.statement.ReturnNode;
import ast.typeNode.VoidTypeNode;
import utils.EnvError;
import utils.Environment;
import utils.SemanticError;
import utils.Utilities;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;

public class FunctionNode implements Node {
    private TypeNode type;
    private IdNode id;
    private DecpNode decp;
    private ArrayList <DecNode> dec;
    private AdecNode adec;
    private ArrayList<StatementNode> statement;

    private String funLabel;
    private String endLabel;
    //TODO: function whit return && CHECK SEMANTICS
    public FunctionNode(TypeNode type, IdNode id, DecpNode decp, ArrayList<DecNode> dec, AdecNode adec, ArrayList<StatementNode> statement) {
        this.type = type;
        this.decp = (decp);
        this.id = id;
        this.dec = dec;
        this.adec = adec;
        this.statement = statement;
        this.funLabel = Utilities.freshLabel();
        this.endLabel = Utilities.freshLabel();
    }

    public String getEndLabel() {
        return endLabel;
    }

    public IdNode getId() {
        return id;
    }

    public DecpNode getDecpNode() {
        return decp;
    }


    public AdecNode getADec() {
        return adec;
    }

    public ArrayList<StatementNode> getStatement() {
        return statement;
    }

    public void setStatement(ArrayList<StatementNode> statement) {
        this.statement = statement;
    }

    public String getFunLabel() {
        return funLabel;
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
     for(Node node: statement){//TODO: control typecheck return
         StatementNode ns = (StatementNode) node;
         if(ns.getStatement() instanceof ReturnNode){
             if(Utilities.isSubtype(type.typeCheck(),new VoidTypeNode())){
                 System.out.println("Declation function as Void type: mustn't be a return stm");
                 System.exit(0);
             }
             else{
                 ReturnNode rn = (ReturnNode) ns.getStatement();
                if(!Utilities.isSubtype(rn.typeCheck(),type.typeCheck())){  //Errore quando viene fatta la "return;"
                    System.out.println("Incompatible type of declaration method: must be a return "+ type.getStringType());
                    System.exit(0);
                };
             }
         }
         else node.typeCheck();
     }
        return null; //TODO: controllare che effettivamente torni null
    }

    @Override
    public String codGeneration() {
        int adecSize = adec != null ? adec.getId().size() : 0;
        int decpSize = decp != null ? decp.getDecp().getListId().size() : 0;
        int paramSize = adecSize + decpSize;
        int decSize = dec != null ? dec.size() : 0;
        String funCode = funLabel + ": //Label of function " + this.id.getId() + "\n"  //label of the function
                       + "mv $sp $fp\n"  // fp <- sp
                       + "push $ra\n";
        //TODO: void function
        for (Node statement : this.statement)
            funCode += statement.codGeneration();
        funCode += endLabel + ": //End Label of function " + this.id.getId() +  "\n";
        funCode += "lw $ra 0($sp)\n" //$ra <- top
                + "pop \n";
        funCode += "addi $sp $sp " + paramSize + " //pop decp & pop adec\n";
        funCode += "addi $sp $sp " + decSize  + " //pop dec \n";
        funCode += "pop //pop the old fp \n";
        funCode += "lw $fp 0($sp)\n" //$fp <-top
                + "pop \n"
                + "jr $ra \n //END OF FUNCTION " + this.id.getId() + "\n"; //Jump at address in $ra
        return funCode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment env) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        //We store for the asset only the number of the assets because we already know the type
        env = Environment.addFunctionDeclaration(env, env.setDecOffset(false), id.getId(), this.type, this); //Declaration of the function
        if(env == null) //if function is already declared
            res.add(new SemanticError(this.id.getId()+": id already declared [function]"));
        else {
            env = Environment.newScope(env);
            //to allow recursion

            /*Aggiungo parametri formali contenuti in Decp*/
            if (decp != null) {
                for (int j = 0; j < decp.getDecp().getListId().size(); j++) {
                    String id_tmp = decp.getDecp().getListId().get(j).getId();
                    if (env.isMultipleDeclared(id_tmp) == EnvError.ALREADY_DECLARED)//verifica se l'identificatore id.get(i) é gia presente
                        //in caso é presente un errore dichiarazione multipla
                        res.add(new SemanticError(id_tmp + " already declared [DecNode]"));
                    else
                        env = Environment.addDeclaration(env, env.setDecOffset(true), id_tmp, new TypeNode(decp.getDecp().getListType().get(j).getStringType()));
                }
            }

            /*Aggiungo parametri formali Asset*/
            if (adec != null) {
                res.addAll(adec.checkSemantics(env));
            }

            /*Aggiungo dichiarazioni in Dec*/
            if (dec != null) {
                for (DecNode decNode : dec) {
                    for (int j = 0; j < decNode.getListId().size(); j++) {
                        String id_tmp = decNode.getListId().get(j).getId();
                        if (env.isMultipleDeclared(id_tmp) == EnvError.ALREADY_DECLARED)//verifica se l'identificatore id.get(i) é gia presente
                            //in caso é presente un errore dichiarazione multipla
                            res.add(new SemanticError(id_tmp + " already declared [DecNode]"));
                        else
                            env = Environment.addDeclaration(env, env.setDecOffset(true), id_tmp, new TypeNode(decNode.getListType().get(j).getStringType()));
                    }
                }
            }

            if (statement != null) {
                for (StatementNode st : statement) {
                    st.setFunNode(this);
                    res.addAll(st.checkSemantics(env));
                    setReturnNode(env,st);
                }
            }

            Environment.exitScope(env);
        }
        return res;
    }

    @Override
    public Environment checkEffects(Environment e) {
        e = Environment.addFunctionDeclaration(e, e.setDecOffset(false) ,id.getId(), this.type,this);
        for(StatementNode stm: statement){
            stm.setFunNode(this);
        }
        return e;
    }


    private void setReturnNode(Environment e,StatementNode st) {
        if (st.getStatement() instanceof ReturnNode) {
            ((ReturnNode) st.getStatement()).setEntry(Environment.lookup(e, id.getId()));
        } else if (st.getStatement() instanceof IteNode itenode) {
            for (Node stm : itenode.getThenStatement()) {
                StatementNode stmNode = (StatementNode) stm;
                if (stmNode.getStatement() instanceof ReturnNode rn) {
                    rn.setEntry(Environment.lookup(e, id.getId()));
                }
            }
            if (itenode.getElseStatement() != null) {
                for (Node stm : itenode.getElseStatement()) {
                    StatementNode stmNode = (StatementNode) stm;
                    if (stmNode.getStatement() instanceof ReturnNode rn) {
                        rn.setEntry(Environment.lookup(e, id.getId()));
                    }
                }
            }
        }
    }
}
