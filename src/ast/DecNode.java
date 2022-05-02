package ast;

import utils.EnvError;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class DecNode implements Node {
    private ArrayList<TypeNode> type;
    private ArrayList<IdNode> id;

    public DecNode(ArrayList<TypeNode> type, ArrayList<IdNode> id) {
        if(type.size() == id.size()) {
            this.type = type;
            this.id = id;
        }else{
            this.type = null;
            this.id = null;
        }
    }

    public void setType(ArrayList<TypeNode> type) {
        this.type = type;
    }

    public void setId(ArrayList<IdNode> id) {
        this.id = id;
    }

    public ArrayList<TypeNode> getListType() {
        return type;
    }

    public ArrayList<IdNode> getListId() {
        return id;
    }

    @Override
    public String toPrint(String indent) {
        String str = "";
            for (int i = 0; i < type.size(); i++) {
                str += type.get(i).toPrint(indent + "  ");
                str += id.get(i).toPrint(indent + " ");
            }
        return indent + "Dec\n" + str + "\n";
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
        for (int j = 0; j < id.size(); j++) {
            String id_tmp = id.get(j).getId();
            if (e.isMultipleDeclared(id_tmp) == EnvError.ALREADY_DECLARED)//verifica se l'identificatore id.get(i) é gia presente
                //in caso é presente un errore dichiarazione multipla
                res.add(new SemanticError(id_tmp + " already declared [DecNode]"));
            else
                e = Environment.addDeclaration(e, id_tmp, new TypeNode(type.get(j).getStringType()),null,0,false);
        }
        return res;
    }
}
