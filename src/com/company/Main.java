package com.company;

import gen.AssetLanLexer;
import gen.AssetLanParser;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        String fileName = "src/codeExamples/example1.assetlan";
        CharStream charStreams = CharStreams.fromFileName(fileName);
        AssetLanLexer assetLanLexer = new AssetLanLexer(charStreams);
        CommonTokenStream tokens = new CommonTokenStream(assetLanLexer);

        List<? extends Token> myTokens = assetLanLexer.getAllTokens();
        List<Token> errors = new ArrayList<>();
        for(int i=0; i < myTokens.size(); i++) {
            int type = myTokens.get(i).getType();
            if(type == 39)
                errors.add(myTokens.get(i));
        }

        FileWriter fileWriter = new FileWriter("Lexical Errors");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(int i=0; i < errors.size(); i++) {
            printWriter.print("Token '" + errors.get(i).getText() + "' " + "at line " + errors.get(i).getLine() + "\n");
        }
        printWriter.close();


        AssetLanParser parser = new AssetLanParser(tokens);
        ParseTree tree = parser.program();


        //Start Lexical Analysis
        /*if(assetLanLexer.lexicalErrors > 0) {
            System.out.println("Houston Abbiamo " + assetLanLexer.lexicalErrors + " problemi");
        }*/


    }
}
