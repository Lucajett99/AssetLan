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
        else if(op.equals("<") || op.equals("<=") || op.equals(">") || op.equals(">=")) {
            if(! (Utilities.isSubtype(left.typeCheck(), new IntTypeNode()) && Utilities.isSubtype(right.typeCheck()
                    , new IntTypeNode()))) {
                System.out.println("Incompatible type error: Must Be IntTypeNode");
                System.exit(0);
            }
            else
                return new BoolTypeNode();
        }
        else if( op.equals("&&") || op.equals("||")) {
            if(! (Utilities.isSubtype(left.typeCheck(), new BoolTypeNode()) && Utilities.isSubtype(right.typeCheck()
                    , new BoolTypeNode()))) {
                System.out.println("Incompatible type error: Must Be BoolTypeNode");
                System.exit(0);
            }
            else
                return new BoolTypeNode();
        }
        else if( op.equals("==") || op.equals("!=")) {
            if(! (Utilities.isSubtype(left.typeCheck(), right.typeCheck()))){
                System.out.println("Incompatible type error: left and right exp must be same type");
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
                //a1 <- top
                   + "lw $a1 0($sp)\n";
        switch (op){
            case "+":
                binExpCode += "add $a0 $a1 $a0 \n";
                break;
            case "-":
                binExpCode += "sub $a0 $a1 $a0 \n";
                break;
            case "*":
                binExpCode += "mult $a0 $a1 $a0 \n";
                break;
            case "/":
                binExpCode += "div $a0 $a1 $a0 \n";
                break;
            case "<":
                binExpCode += "lt $a0 $a1 $a0 \n";
                break;
            case "<=":
                binExpCode += "le $a0 $a1 $a0 \n";
                break;
            case ">":
                binExpCode += "gt $a0 $a1 $a0 \n";
                break;
            case ">=":
                binExpCode += "ge $a0 $a1 $a0 \n";
                break;
            case "==":
                binExpCode += "eq $a0 $a1 $a0 \n";
                break;
            case "!=":
                binExpCode += "eq $a0 $a1 $a0 \n";
                binExpCode += "not $a0 $a0 \n";
                break;
            case "&&":
                binExpCode += "and $a0 $a1 $a0 \n";
                break;
            case "||":
                binExpCode += "or $a0 $a1 $a0 \n";
                break;
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
    public Environment checkEffects(Environment e) {
        Environment e1 = left.checkEffects(e);
        return right.checkEffects(e1);
    }

    @Override
    public int evaluateExp(){
        int l = left.evaluateExp();
        int r = right.evaluateExp();
        switch (op){
            case "+":
                return l + r;
            case "-":
                return l - r;
            case "/":
                if(r == 0) {
                    System.out.println("Division by 0 error");
                    System.exit(0);
                }
                else
                    return l / r;
            case "*":
                return l * r;
        }
        return 0;
    }
}
