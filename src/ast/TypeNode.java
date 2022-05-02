package ast;

import ast.typeNode.BoolTypeNode;
import ast.typeNode.IntTypeNode;
import ast.typeNode.VoidTypeNode;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;
import java.util.Objects;

public class TypeNode implements Node{
    private String type;

    public TypeNode(String type) {
        this.type = type;
    }

    public String getStringType() {
        return type;
    }

    @Override
    public String toPrint(String indent) {
       return indent + this.type + " ";
    }

    @Override
    public Node typeCheck() {
        switch (type){
            case "int":
                return new IntTypeNode();
            case "bool":
                return new BoolTypeNode();
            case "void":
                return new VoidTypeNode();
            default:
                System.out.println("Error TypeNode Conversion : Impossible convert type "+ type);
                return null;
        }
    }

    @Override
    public String codGeneration() {
        return null;
    }

    @Override
    public ArrayList<SemanticError> checkSemantics(Environment e) {
        return null;
    }
}
