package ast;

import utils.Environment;
import utils.SemanticError;
import java.util.ArrayList;

public interface Node {
    String toPrint(String indent);

    Node typeCheck();

    String codGeneration();

    ArrayList<SemanticError> checkSemantics(Environment e);

    Environment checkEffects(Environment e);

    default int evaluateExp() {
        return 0;
    }
}