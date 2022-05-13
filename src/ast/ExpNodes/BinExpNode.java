package ast.ExpNodes;

import ast.Node;
import ast.typeNode.BoolTypeNode;
import ast.typeNode.IntTypeNode;
import utils.Environment;
import utils.SemanticError;
import utils.Utilities;

import java.util.ArrayList;

public class BinExpNode implements Node {
    private String op;
    private Node left;
    private Node right;

    public BinExpNode(String op, Node left, Node right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toPrint(String indent) {
        String str="";
        str += left.toPrint(indent+"  ");
        str += op;
        str += left.toPrint(indent+"  ");
        return indent+"BinExp\n" + str;
    }

    @Override
    public Node typeCheck() {
        if(op.equals("*") || op.equals("/") || op.equals("+") || op.equals("-")) {
            if(! (Utilities.isSubtype(left.typeCheck(), new IntTypeNode()) && Utilities.isSubtype(right.typeCheck()
            , new IntTypeNode()))) {
                System.out.println("Incompatible type error: Must Be IntTypeNode");
                System.exit(0);
            }
            else
                return new IntTypeNode();
        }
        else if(op.equals("<") || op.equals("<=") || op.equals(">") || op.equals(">=") || op.equals("==") || op.equals("!=")) {
            if(! (Utilities.isSubtype(left.typeCheck(), new IntTypeNode()) && Utilities.isSubtype(right.typeCheck()
                    , new IntTypeNode()))) {
                System.out.println("Incompatible type error: Must Be IntTypeNode");
                System.exit(0);
            }
            else
                return new BoolTypeNode();
        }
        else if(op.equals("==") || op.equals("!=") || op.equals("&&") || op.equals("||")) {
            if(! (Utilities.isSubtype(left.typeCheck(), new BoolTypeNode()) && Utilities.isSubtype(right.typeCheck()
                    , new BoolTypeNode()))) {
                System.out.println("Incompatible type error: Must Be BoolTypeNode");
                System.exit(0);
            }
            else
                return new BoolTypeNode();
        }
        return null;
    }

    @Override
    public String codGeneration() {
        String binExpCode = "";
        binExpCode += left.codGeneration() + "\n"
                   + "push $a0 \n"
                   + right.codGeneration() + "\n"
                   + "lw $t1 0($sp)"; // load the left operand in $t1
        switch (op){
            case "+":
                binExpCode += "add $a0 $t1 $a0 \n";
            case "-":
                binExpCode += "sub $a0 $t1 $a0 \n";
            case "*":
                binExpCode += "mult $a0 $t1 $a0 \n";
            case "/":
                binExpCode += "div $a0 $t1 $a0 \n";
            case "<":
                binExpCode += "lt $a0 $t1 $a0 \n";
            case "<=":
                binExpCode += "le $a0 $t1 $a0 \n";
            case ">":
                binExpCode += "gt $a0 $t1 $a0 \n";
            case ">=":
                binExpCode += "ge $a0 $t1 $a0 \n";
            case "==":
                binExpCode += "eq $a0 $t1 $a0 \n";
            case "!=":
                binExpCode += "eq $a0 $t1 $a0 \n";
                binExpCode += "not $a0 $a0 \n";
            case "&&":
                binExpCode += "and $a0 $t1 $a0 \n";
            case "||":
                binExpCode += "or $a0 $t1 $a0 \n";
        }
        binExpCode += "pop \n";
        return binExpCode;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        ArrayList<SemanticError> res = new ArrayList<SemanticError>();
        if(left!=null){
            res.addAll(left.checkSemantics(e));
        }
        if(right !=null){
            res.addAll(right.checkSemantics(e));
        }
        return res;
    }

    @Override
    public ArrayList<String> checkEffects(Environment e) {
        return null;
    }
}
