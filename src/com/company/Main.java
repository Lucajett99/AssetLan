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
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws Exception {
        /*try {
            System.out.println("===== AssetLan Compiler&Interpreter ======");
            if (args.length == 0) {
                System.err.println("No file to compile & run provided.");
                System.exit(0);
            }
            String fileName = args[0];
            if (!Paths.get(fileName).toFile().exists()) {
                throw new FileNotFoundException("File: " + fileName + " not found.");
            }*/
            String fileName = "src/codeExamples/example5.assetlan";
            CharStream charStreams = CharStreams.fromFileName(fileName);
            AssetLanLexer lexer = new AssetLanLexer(charStreams);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            AssetLanParser parser = new AssetLanParser(tokens);
            System.out.println("Check for Lexical and Syntax Errors : ");
            SyntaxErrorListener errorListener = new SyntaxErrorListener();
            lexer.removeErrorListeners();
            lexer.addErrorListener(errorListener);
            parser.removeErrorListeners();
            parser.addErrorListener(errorListener);

            AssetLanVisitorImpl visitor = new AssetLanVisitorImpl();
            Node ast = visitor.visit(parser.program());

            if (errorListener.getSyntaxErrors().size() == 0) {
                /*System.out.println("Visualizing AST...");
                System.out.println(ast.toPrint(""));*/
                System.out.println("OK");
                Environment env = new Environment();
                System.out.print("Check for Semantic Error : ");
                ArrayList<SemanticError> err = ast.checkSemantics(env);
                if (!err.isEmpty()) {
                    for (SemanticError e : err) {
                        System.out.println(e.msg);
                    }
                } else {
                    System.out.println("OK");
                    System.out.print("Type Checking : ");
                    Node type = ast.typeCheck();
                    System.out.println("OK");
                    System.out.print("Check for Liquidity Property : ");
                    Environment envEffects = ast.checkEffects(new Environment());
                    System.out.println("OK");
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
      /*      } catch (Exception exc) {
                System.err.println(exc.getMessage());
                System.exit(2);
            }*/
        }
}