package ast;

import ast.ExpNodes.*;
import gen.AssetLanBaseVisitor;
import gen.AssetLanParser.*;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReferenceArray;

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

    /*---------------Marco-------------*/
    @Override
    public Node visitAsset(AssetContext ctx) {
        String id = ctx.ID().getText();
        return new AssetNode(id);
    }

    @Override
    public Node visitFunction(FunctionContext ctx) {
        Node type = visit(ctx.type());
        String id = ctx.ID().getText();
        Node adec = null;
        ArrayList<Node> dec = new ArrayList<Node>();
        ArrayList<Node> statement = new ArrayList<Node>();

        for (DecContext dc : ctx.dec()) {
            dec.add(visit(dc));
        }
        if(ctx.adec() != null) {
            adec  = visit(ctx.adec());
        }
        for (StatementContext sc : ctx.statement()) {
            statement.add(visit(sc));
        }
        return new FunctionNode(type, id, dec, adec, statement);

    }

    @Override
    public Node visitDec(DecContext ctx) {
        ArrayList<Node> type = new ArrayList<Node>();
        ArrayList<String> id = new ArrayList<String>();
        for (TypeContext tc : ctx.type()) {
            type.add(visit(tc));
        }
        for (String str : ctx.getText()) {  //TODO:  Handle ID*
            id.add(visit(str));
        }
        return new DecNode(type, id);
    }

    @Override
    public Node visitAdec(AdecContext ctx) {
       ArrayList<String> id = new ArrayList<String>();
        for (String str : ctx.getText()) {  //TODO:  Handle ID*
            id.add(visit(str));
        }
        return new AdecNode(id);
    }

    @Override
    public Node visitStatement(StatementContext ctx) {
        Node res;
        if(ctx.assignment()!=null){
            res = visit(ctx.assignment());
        }
        else if(ctx.move()!=null){
            res = visit(ctx.move());
        }
        else if(ctx.print()!=null){
            res = visit(ctx.print());
        }
        else if(ctx.transfer()!=null){
            res = visit(ctx.transfer());
        }
        else if(ctx.ret()!=null){
            res = visit(ctx.ret());
        }
        else if(ctx.ite()!=null){
            res = visit(ctx.ite());
        }
        else if(ctx.call()!=null){
            res = visit(ctx.call());
        }
        else{
            return null;
        }
        return new StatementNode(res);
    }

    @Override
    public Node visitType(TypeContext ctx) {
        if(ctx.getText().equals("int"))
            return new IntTypeNode();
        else if(ctx.getText().equals("bool"))
            return new BoolTypeNode();
        return null;
    }

    @Override
    public Node visitAssignment(AssignmentContext ctx) {
        String id = ctx.ID().getText();
        Node exp = visit(ctx.exp());
        return new AssignmentNode(id,exp);
    }

    /*---------------Simone-------------*/

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

    /*---------------Luca-------------*/

    @Override
    public Node visitBaseExp(BaseExpContext ctx) {
        Node exp = visit(ctx.exp());
        return new BaseExpNode(exp);
    }

    @Override
    public Node visitBinExp(BinExpContext ctx) {
        String op = ctx.op.getText();
        Node left = visit(ctx.left);
        Node right = visit(ctx.right);
        return new BinExpNode(op, left, right);
    }

    @Override
    public Node visitDerExp(DerExpContext ctx) {
        String id = ctx.ID().toString();
        return new DerExpNode(id);
    }

    @Override
    public Node visitValExp(ValExpContext ctx) {
        int number = Integer.parseInt(ctx.NUMBER().toString());
        return new ValExpNode(number);
    }

    @Override
    public Node visitNegExp(NegExpContext ctx) {
        Node exp = visit(ctx.exp());
        return new NegExpNode(exp);
    }

    @Override
    public Node visitBoolExp(BoolExpContext ctx) {
        boolean bool = Boolean.parseBoolean(ctx.BOOL().getText());
        return new BoolExpNode(bool);
    }

    @Override
    public Node visitCallExp(CallExpContext ctx) {
        Node call = visit(ctx.call());
        return new CallExpNode(call);
    }

    @Override
    public Node visitNotExp(NotExpContext ctx) {
        Node exp = visit(ctx.exp());
        return new NotExpNode(exp);
    }

    /*------------------------------------------------------------------------*/

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
}
