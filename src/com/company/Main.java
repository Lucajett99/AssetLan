package com.company;

import gen.AssetLanLexer;
import gen.AssetLanParser;
import gen.AssetLanVisitor;
import org.antlr.v4.misc.Graph;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.Interval;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

public class Main {

    public static void main(String[] args) throws Exception {
        String fileName = "src/codeExamples/example1.assetlan";
        CharStream charStreams = CharStreams.fromFileName(fileName);
        AssetLanLexer assetLanLexer = new AssetLanLexer(charStreams);
        CommonTokenStream tokens = new CommonTokenStream(assetLanLexer);
        AssetLanParser parser = new AssetLanParser(tokens);
        ParseTree tree = parser.program();
        //Start Lexical Analysis
        if(assetLanLexer.lexicalErrors > 0) {
            System.out.println("Houston Abbiamo " + assetLanLexer.lexicalErrors + " problemi");
        }


    }
}
