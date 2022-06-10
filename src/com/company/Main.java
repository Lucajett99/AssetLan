package com.company;

import ast.AssetLanVisitorImpl;
import ast.Node;
import gen.AssetLanLexer;
import gen.AssetLanParser;
import gen.interpreter.ExecuteVM;
import interpreter.SVMLexer;
import interpreter.SVMParser;
import gen.interpreter.SVMVisitorImpl;
import org.antlr.v4.runtime.*;
import utils.Environment;
import utils.SemanticError;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        String fileName = "src/codeExamples/example10.assetlan";
        CharStream charStreams = CharStreams.fromFileName(fileName);
        AssetLanLexer lexer = new AssetLanLexer(charStreams);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AssetLanParser parser = new AssetLanParser(tokens);

        SyntaxErrorListener errorListener = new SyntaxErrorListener();
        lexer.addErrorListener(errorListener);
        parser.addErrorListener(errorListener);

        AssetLanVisitorImpl visitor = new AssetLanVisitorImpl();
        Node ast = visitor.visit(parser.program());

        if (errorListener.getSyntaxErrors().size() == 0) {
            /*System.out.println("Visualizing AST...");
            System.out.println(ast.toPrint(""));*/

            Environment env = new Environment();
            ArrayList<SemanticError> err = ast.checkSemantics(env);
            if (!err.isEmpty()) {
                for (SemanticError e : err) {
                    System.out.println(e.msg);
                }
            } else {
                Node type = ast.typeCheck();
               // Environment envEffects = ast.checkEffects(new Environment());

                String code = ast.codGeneration();
                BufferedWriter out = new BufferedWriter(new FileWriter(fileName + ".asm"));
                out.write(code);
                out.close();
                System.out.println("Code generated! Assembling and running generated code.");

                CharStream SVMcharStream = CharStreams.fromFileName(fileName + ".asm");
                SVMLexer svmLexer = new SVMLexer(SVMcharStream);
                CommonTokenStream tokensASM = new CommonTokenStream(svmLexer);
                SVMParser svmParser = new SVMParser(tokensASM);

                SyntaxErrorListener svmErrorListener = new SyntaxErrorListener();
                svmLexer.addErrorListener(svmErrorListener);
                svmParser.addErrorListener(svmErrorListener);

                SVMVisitorImpl svmVisitor = new SVMVisitorImpl();
                svmVisitor.visit(svmParser.assembly());
                if (errorListener.getSyntaxErrors().size() == 0) {
                    System.out.println("Starting Virtual Machine...");
                    ExecuteVM vm = new ExecuteVM(svmVisitor.getCode());
                    vm.cpu();
                } else {
                    System.out.println("Error Starting VM");
                }
            }
        }
    }
}