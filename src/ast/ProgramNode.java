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
        //I presume I have a register $b (balance) in which i will store the aggregate sum of all the transfer in the program
        return  "li $b 0 \n" + fieldCode + assetCode +  initcallCode + "print $b\n" + "halt\n"+ functionCode ;
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
        Environment.exitScope(e);
        return res;
    }

    @Override
    public Environment checkEffects(Environment e) {
        e = Environment.newScope(e);
        if(assets != null){
            for (Node node: assets) {
                e = node.checkEffects(e);
            }
        }
        if(functions != null){
            for(Node fun : functions){
                e = fun.checkEffects(e);
            }
        }
        e = initcall.checkEffects(e);

        if(Environment.checkGlobalLiquidity(e) > 0) {
            System.out.println("\nIl programma non e' liquido: " + Environment.checkGlobalLiquidity(e) + " asset globali non sono stati svuotati\n");
            System.exit(0);
        }
        else {
            System.out.println("\nIl programma e' liquido!\n");
        }

        Environment.exitScope(e);
        return e;

    }
}

