package ast;

import utils.EnvError;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class FunctionNode implements Node {
    private TypeNode type;
    private IdNode id;
    private DecpNode decp;
    private DecNode dec;
    private AdecNode adec;
    private ArrayList<Node> statement;

    public FunctionNode(Node type, IdNode id, DecpNode decp, DecNode dec, AdecNode adec, ArrayList<Node> statement) {
        if (type != null) {
            this.type = (TypeNode) type;
        }
        else
            this.type = new TypeNode("void");
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
        for (Node dec : dec.getListId())
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
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(e.isInsideFunct()){
           res.add(new SemanticError("isn't possibile declared a function"));
           return res;
        }else{
            Environment env = Environment.addDeclaration(e,id.getId(),new TypeNode("function"));
            if(env ==null){
                res.add(new SemanticError(this.id.getId()+": function already declared"));
                return res;
            }else{
                env = Environment.newScope(env);
                env.setInsideFunct(true);

                /*Aggiungo parametri formali contenuti in Decp*/
                ArrayList<IdNode> id = this.decp.getDecp().getListId();
                ArrayList<TypeNode> type = this.decp.getDecp().getListType();
                for(int i = 0; i< id.size();i++){
                    if(env.isMultipleDeclared(id.get(i).getId())== EnvError.ALREADY_DECLARED)//verifica se l'identificatore id.get(i) é gia presente
                        //in caso é presente un errore dichiarazione multipla
                        res.add(new SemanticError(id.get(i).getId()+" already declared"));

                    else
                        env = Environment.addDeclaration(env,id.get(i).getId(),type.get(i));
                }

                /*Aggiungo parametri formali Asset*/
                ArrayList<IdNode> assetid= this.adec.getId();
                for (IdNode idAss:assetid) {
                    if(env.isMultipleDeclared(idAss.getId())==EnvError.ALREADY_DECLARED){
                        res.add(new SemanticError(idAss.getId()+": already declared"));
                    }
                    env = Environment.addDeclaration(env, idAss.getId(), new TypeNode("asset"));
                }


                /*Aggiungo dichiarazioni in Dec*/
                for(int i = 0; i< dec.getListId().size(); i++){
                    if(env.isMultipleDeclared(dec.getListId().get(i).getId())== EnvError.ALREADY_DECLARED)//verifica se l'identificatore id.get(i) é gia presente
                        //in caso é presente un errore dichiarazione multipla
                        res.add(new SemanticError(id.get(i).getId()+" already declared"));

                    else
                        env = Environment.addDeclaration(env,id.get(i).getId(),type.get(i));
                }
                if(statement != null){
                    for (Node st: statement ) {
                        st.checkSemantics(env);
                    }
                }
                Environment.exitScope(env);
                env.setInsideFunct(false);
                return res;
            }

        }
    }
}
