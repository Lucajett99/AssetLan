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
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(e.isInsideFunct()){
           res.add(new SemanticError("isn't possibile declared a function"));
           return res;
        }else{
            Environment env = Environment.addDeclaration(e, id.getId(), type);
            if(env == null){ //if function is already declared
                res.add(new SemanticError(this.id.getId()+": function already declared"));
                return res;
            }else{
                env = Environment.newScope(env);
                env.setInsideFunct(true);

                /*Aggiungo parametri formali contenuti in Decp*/
                ArrayList<IdNode> id = this.decp.getDecp().getListId();
                ArrayList<TypeNode> type = this.decp.getDecp().getListType();
                for(int i = 0; i< id.size();i++){
                    if(env.isMultipleDeclared(id.get(i).getId()) == EnvError.ALREADY_DECLARED)//verifica se l'identificatore id.get(i) é gia presente
                        //in caso é presente un errore dichiarazione multipla
                        res.add(new SemanticError(id.get(i).getId()+" already declared [params]"));

                    else
                        env = Environment.addDeclaration(env,id.get(i).getId(),type.get(i));
                }

                /*Aggiungo parametri formali Asset*/
                if(adec != null) {
                    ArrayList<IdNode> assetid = adec.getId();

                    for (IdNode idAss : assetid) {
                        if (env.isMultipleDeclared(idAss.getId()) == EnvError.ALREADY_DECLARED) {
                            res.add(new SemanticError(idAss.getId() + ": already declared [asset]"));
                        }
                        env = Environment.addDeclaration(env, idAss.getId(), new TypeNode("asset"));
                    }
                }

                /*Aggiungo dichiarazioni in Dec*/
                if(dec != null) {
                    for (int i = 0; i < dec.size(); i++) {
                        for (int j = 0; j < dec.get(i).getListId().size(); j++) {
                            String id_tmp = dec.get(i).getListId().get(j).getId();
                            if (env.isMultipleDeclared(id_tmp) == EnvError.ALREADY_DECLARED)//verifica se l'identificatore id.get(i) é gia presente
                                //in caso é presente un errore dichiarazione multipla
                                res.add(new SemanticError(id_tmp + " already declared [declaration]"));

                            else
                                env = Environment.addDeclaration(env, id_tmp, type.get(i));

                        }
                    }
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
