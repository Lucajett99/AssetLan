package com.company;

import ast.AssetLanVisitorImpl;
import ast.Node;
import gen.AssetLanLexer;
import gen.AssetLanParser;
import org.antlr.v4.runtime.*;
import utils.Environment;
import utils.SemanticError;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        String fileName = "src/codeExamples/example1.assetlan";
        CharStream charStreams = CharStreams.fromFileName(fileName);
        AssetLanLexer lexer = new AssetLanLexer(charStreams);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AssetLanParser parser = new AssetLanParser(tokens);

        SyntaxErrorListener errorListener = new SyntaxErrorListener();
        lexer.addErrorListener(errorListener);
        parser.addErrorListener(errorListener);

        AssetLanVisitorImpl visitor = new AssetLanVisitorImpl();
        Node ast = visitor.visit(parser.program());
        System.out.println("Visualizing AST...");
        System.out.println(ast.toPrint(""));

        Environment env = new Environment();
        ArrayList<SemanticError> err = ast.checkSemantics(env);
        if(err != null) {
            for (SemanticError e : err) {
                System.out.println(e.msg);
            }
        }
    }
}