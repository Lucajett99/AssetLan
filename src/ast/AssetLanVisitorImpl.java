package ast;

import gen.AssetLanBaseVisitor;
import gen.AssetLanParser.*;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;

public class AssetLanVisitorImpl extends AssetLanBaseVisitor<Node> {
    @Override
    public Node visitProgram(ProgramContext ctx) {
        ProgramNode res;
        ArrayList<Node> fields = new ArrayList<Node>();
        ArrayList<Node> assets = new ArrayList<Node>();
        ArrayList<Node> functions = new ArrayList<Node>();

        for (FieldContext fc : ctx.field()) {
            fields.add(visit(fc));
        }
        for (AssetContext ac : ctx.asset()) {
            assets.add(visit(ac));
        }
        for (FunctionContext fuc : ctx.function()) {
            functions.add(visit(fuc));
        }
        Node initcall = visit(ctx.initcall());
        res = new ProgramNode(fields, assets, functions, initcall);
        return res;
    }

    @Override
    public Node visitField(FieldContext ctx) {
        Node type = visit(ctx.type());
        String id = ctx.ID().getText();
        Node exp = null;
        if(ctx.exp() != null)
           exp  = visit(ctx.exp());
        return new FieldNode(type, id, exp);
    }

    @Override
    public Node visitAsset(AssetContext ctx) {
        return super.visitAsset(ctx);
    }

    @Override
    public Node visitFunction(FunctionContext ctx) {
        return super.visitFunction(ctx);
    }

    @Override
    public Node visitDec(DecContext ctx) {
        return super.visitDec(ctx);
    }

    @Override
    public Node visitAdec(AdecContext ctx) {
        return super.visitAdec(ctx);
    }

    @Override
    public Node visitStatement(StatementContext ctx) {
        return super.visitStatement(ctx);
    }

    @Override
    public Node visitType(TypeContext ctx) {
        return super.visitType(ctx);
    }

    @Override
    public Node visitAssignment(AssignmentContext ctx) {
        return super.visitAssignment(ctx);
    }

    @Override
    public Node visitMove(MoveContext ctx) {
        return super.visitMove(ctx);
    }

    @Override
    public Node visitPrint(PrintContext ctx) {
        return super.visitPrint(ctx);
    }

    @Override
    public Node visitTransfer(TransferContext ctx) {
        return super.visitTransfer(ctx);
    }

    @Override
    public Node visitRet(RetContext ctx) {
        return super.visitRet(ctx);
    }

    @Override
    public Node visitIte(IteContext ctx) {
        return super.visitIte(ctx);
    }

    @Override
    public Node visitCall(CallContext ctx) {
        return super.visitCall(ctx);
    }

    @Override
    public Node visitInitcall(InitcallContext ctx) {
        return super.visitInitcall(ctx);
    }

    @Override
    public Node visitBaseExp(BaseExpContext ctx) {
        return super.visitBaseExp(ctx);
    }

    @Override
    public Node visitBinExp(BinExpContext ctx) {
        return super.visitBinExp(ctx);
    }

    @Override
    public Node visitDerExp(DerExpContext ctx) {
        return super.visitDerExp(ctx);
    }

    @Override
    public Node visitValExp(ValExpContext ctx) {
        return super.visitValExp(ctx);
    }

    @Override
    public Node visitNegExp(NegExpContext ctx) {
        return super.visitNegExp(ctx);
    }

    @Override
    public Node visitBoolExp(BoolExpContext ctx) {
        return super.visitBoolExp(ctx);
    }

    @Override
    public Node visitCallExp(CallExpContext ctx) {
        return super.visitCallExp(ctx);
    }

    @Override
    public Node visitNotExp(NotExpContext ctx) {
        return super.visitNotExp(ctx);
    }

    @Override
    public Node visit(ParseTree tree) {
        return super.visit(tree);
    }

    @Override
    public Node visitChildren(RuleNode node) {
        return super.visitChildren(node);
    }

    @Override
    public Node visitTerminal(TerminalNode node) {
        return super.visitTerminal(node);
    }

    @Override
    public Node visitErrorNode(ErrorNode node) {
        return super.visitErrorNode(node);
    }

    @Override
    protected Node defaultResult() {
        return super.defaultResult();
    }

    @Override
    protected Node aggregateResult(Node aggregate, Node nextResult) {
        return super.aggregateResult(aggregate, nextResult);
    }

    @Override
    protected boolean shouldVisitNextChild(RuleNode node, Node currentResult) {
        return super.shouldVisitNextChild(node, currentResult);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
