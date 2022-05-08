package ast;

import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class ProgramNode implements Node {
    private ArrayList<Node> fields;
    private ArrayList<Node> assets;
    private ArrayList<Node> functions;
    private Node initcall;

    public ProgramNode(ArrayList<Node> fields, ArrayList<Node> assets, ArrayList<Node> functions, Node initcall) {
        this.fields = fields;
        this.assets = assets;
        this.functions = functions;
        this.initcall = initcall;
    }

    @Override
    public String toPrint(String indent) {
        String str="";
        for (Node field:fields)
            str += field.toPrint(indent+"  ");
        for (Node asset:assets)
            str += asset.toPrint(indent+"  ");
        for (Node function:functions)
            str += function.toPrint(indent+"  ");
        return indent+"Program\n" + str + initcall.toPrint(indent+"  ") ;
    }

    @Override
    public Node typeCheck() {
        for (Node fields:fields) {
            fields.typeCheck();
        }
        for (Node asset:assets) {
            asset.typeCheck();
        }
        for (Node function:functions) {
            function.typeCheck();
        }
        return initcall.typeCheck();
    }

    @Override
    public String codGeneration() {
        String fieldCode = "";
        String assetCode = "";
        String functionCode = "";
        String initcallCode = "";

        for(Node field : fields)
            fieldCode += field.codGeneration();

        for(Node asset : assets)
            assetCode += asset.codGeneration();

        for(Node function : functions)
            functionCode += function.codGeneration();

        initcallCode = initcall.codGeneration();

        return  fieldCode + assetCode + functionCode + initcallCode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        e = Environment.newScope(e);
        if(fields!= null){
            for (Node node: fields) {
                res.addAll(node.checkSemantics(e));
            }
        }
        if(assets != null){
            for (Node node: assets) {
                res.addAll(node.checkSemantics(e));
            }
        }
        if(functions != null){
            for (Node node: functions) {
                res.addAll(node.checkSemantics(e));
            }
        }
        res.addAll(initcall.checkSemantics(e));

        return res;
    }

    @Override
    public ArrayList<String> checkEffects(Environment e) {
        ArrayList<String> res = new ArrayList<>();
        e = Environment.newScope(e);
        if(assets!= null){
            for (Node node: assets) {
                res.addAll(node.checkEffects(e));
            }
        }
        if(functions!= null){
            for (Node node: functions) {
                res.addAll(node.checkEffects(e));
            }
        }
        //res.addAll(initcall.checkEffects(e));
        return res;
        }
}

