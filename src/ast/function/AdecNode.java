package ast.function;

import ast.AssetNode;
import ast.IdNode;
import ast.Node;
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
    //There is no code generation for asset declaration
    public String codGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        int offset;
       /* if(id != null) {
            for (IdNode idAss : id) {
                if (e.isMultipleDeclared(idAss.getId()) == EnvError.ALREADY_DECLARED) {
                    res.add(new SemanticError(idAss.getId() + " : already declared [adec]"));
                } else {
                    offset = e.setDecOffset(true);
                    e = Environment.addDeclaration(e, offset, idAss.getId(), new AssetNode(idAss));
                    offsetList.add(offset);
                }
            }
        }*/
        if(id != null) {
            for (int i = id.size() -1; i >= 0; i--){
                if (e.isMultipleDeclared(id.get(i).getId()) == EnvError.ALREADY_DECLARED) {
                    res.add(new SemanticError(id.get(i).getId() + " : already declared [adec]"));
                } else {
                    offset = e.setDecOffset(true);
                    e = Environment.addDeclaration(e, offset, id.get(i).getId(), new AssetNode(id.get(i)).typeCheck());
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
