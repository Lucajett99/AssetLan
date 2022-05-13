package ast;

import utils.EnvError;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class AdecNode implements Node {
    private ArrayList<IdNode> id;

    public AdecNode(ArrayList<IdNode> id) {
        this.id = id;
    }

    public ArrayList<IdNode> getId() {
        return id;
    }

    public void setId(ArrayList<IdNode> id) {
        this.id = id;
    }

    @Override
    public String toPrint(String indent) {
        String str = "";
        for (IdNode id : id) {
            str += id.toPrint(indent) + " ";
        }
        return  indent +"Adec\n" + str;
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
        if(id != null) {
            for (IdNode idAss : id) {
                if (e.isMultipleDeclared(idAss.getId()) == EnvError.ALREADY_DECLARED) {
                    res.add(new SemanticError(idAss.getId() + " : already declared [adec]"));
                } else {
                    e = Environment.addDeclaration(e, e.setDecOffset(), idAss.getId(), new AssetNode(idAss));
                }
            }
        }
        return res;
    }

    @Override
    public ArrayList<String> checkEffects(Environment e) {
        ArrayList<String> res = new ArrayList<String>();
        if(id != null) {
            for (IdNode idAss : id) {
                if (e.isMultipleDeclared(idAss.getId()) == EnvError.ALREADY_DECLARED) {
                    res.add(idAss.getId() + " : already declared [adec]");
                } else {
                    e = Environment.addDeclaration(e,e.setDecOffset() ,idAss.getId(), new AssetNode(idAss));
                    Environment.lookup(e,idAss.getId()).setLiquidity(1);
                }
            }
        }
        return res;
    }
}
