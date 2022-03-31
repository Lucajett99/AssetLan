package com.company;

import org.antlr.v4.runtime.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SyntaxErrorListener extends BaseErrorListener {
    private final List<SyntaxError> SyntaxErrors = new ArrayList();

    public SyntaxErrorListener() {}

    private List<SyntaxError> getSyntaxErrors() {
        return this.SyntaxErrors;
    }

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
        super.syntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e);
        this.SyntaxErrors.add(new SyntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e));
        try {
            PrintWriter out = new PrintWriter("LexicalErrors.txt");
            Iterator errors = this.getSyntaxErrors().iterator();

            while(errors.hasNext()) {
                SyntaxError i = (SyntaxError)errors.next();
                out.println("Error: " + i.getMsg() + " at line " + i.getLine());
            }
            out.close();
        } catch (FileNotFoundException var10) {
            e.printStackTrace();
        }
    }
}

