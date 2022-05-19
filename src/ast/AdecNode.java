package ast;

import utils.EnvError;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class AdecNode implements Node {
    private ArrayList<IdNode> id;
    private ArrayList<Integer> offsetList;

    public AdecNode(ArrayList<IdNode> id) {
        this.id = id;
        this.offsetList = new ArrayList<Integer>();
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
        int offset;
        if(id != null) {
            for (IdNode idAss : id) {
                if (e.isMultipleDeclared(idAss.getId()) == EnvError.ALREADY_DECLARED) {
                    res.add(new SemanticError(idAss.getId() + " : already declared [adec]"));
                } else {
                    offset = e.setDecOffset();
                    e = Environment.addDeclaration(e, e.setDecOffset(), idAss.getId(), new AssetNode(idAss));
                    offsetList.add(offset);
                }
            }
        }
        return res;
    }

    @Override
    public Environment checkEffects(Environment e) {
        return null;
    }
}
