package com.company;

import gen.AssetLanBaseVisitor;
import gen.AssetLanLexer;
import gen.AssetLanParser;
import gen.AssetLanVisitor;
import org.antlr.v4.runtime.*;

public class Main {

    public static void main(String[] args) throws Exception {
        String fileName = "src/codeExamples/example1.assetlan";
        CharStream charStreams = CharStreams.fromFileName(fileName);
        AssetLanLexer lexer = new AssetLanLexer(charStreams);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        AssetLanParser parser = new AssetLanParser(tokens);

        //create the listener and adding it to the lexer
        lexer.removeErrorListeners();
        SyntaxErrorListener errorListener = new SyntaxErrorListener();
        lexer.addErrorListener(errorListener);

        //creation the visitor and get context
        AssetLanVisitor visitor = new AssetLanBaseVisitor();
        visitor.visitProgram(parser.program());
    }
}