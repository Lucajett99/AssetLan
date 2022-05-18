package ast;

import ast.ExpNodes.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import gen.AssetLanBaseVisitor;
import gen.AssetLanParser.*;

import gen.AssetLanParser;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

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
        Node initcall = null;
 //       if(ctx.initcall().exception == null)
            initcall = visit(ctx.initcall());

        res = new ProgramNode(fields, assets, functions, initcall);
        return res;
    }

    @Override
    public Node visitField(FieldContext ctx) {
        Node type = visit(ctx.type());
        IdNode id = new IdNode(ctx.ID().getText());
        Node exp = null;
        if(ctx.exp() != null)
           exp  = visit(ctx.exp());
        return new FieldNode(type, id, exp);
    }

    /*---------------Marco-------------*/
    @Override
    public Node visitAsset(AssetContext ctx) {
        IdNode id = new IdNode(ctx.ID().getText());
        return new AssetNode(id);
    }

    @Override
    public Node visitFunction(FunctionContext ctx) {
        TypeNode type = ctx.type() != null ? (TypeNode) (visit(ctx.type())) : new TypeNode("void");
        IdNode id = new IdNode(ctx.ID().toString());
        ArrayList<TypeNode> decpType = new ArrayList<TypeNode>();
        ArrayList<IdNode> decpId = new ArrayList<IdNode>();
        DecpNode decp = null;

        if(ctx.decp() != null) {
            DecNode decNode = (DecNode) visit(ctx.decp());
            decp = new DecpNode(decNode);
        }

        AdecNode adec = ctx.adec() != null ? (AdecNode) visit(ctx.adec()) : null;

        ArrayList<DecNode> dec = new ArrayList<DecNode>();
        for(int i = 0; i < ctx.dec().size(); i++) {
            DecNode dec_i = (DecNode) visit(ctx.dec(i)); //ith declaration
            dec.add(dec_i);
        }

        ArrayList<StatementNode> statement = new ArrayList<StatementNode>();
        for(StatementContext st: ctx.statement()) {
            statement.add((StatementNode) visit(st));
        }

        return new FunctionNode(type, id, decp, dec, adec, statement);
    }

    @Override
    public Node visitDec(AssetLanParser.DecContext ctx) {
        ArrayList<TypeNode> type = new ArrayList<TypeNode>();
        ArrayList<IdNode> id = new ArrayList<IdNode>();
        for (TypeContext tc : ctx.type()) {
            type.add( (TypeNode) visit(tc));
        }
        for (TerminalNode inode : ctx.ID()) {
            id.add(new IdNode(inode.getText()));
        }
        return new DecNode(type, id);
    }

    @Override
    public Node visitAdec(AdecContext ctx) {
       ArrayList<IdNode> id = new ArrayList<IdNode>();
        for (TerminalNode node : ctx.ID()) {
            id.add(new IdNode(node.getText()));
        }
        return new AdecNode(id);
    }

    @Override
    public Node visitStatement(StatementContext ctx) {
        Node res;
        if(ctx.assignment() != null){
            res = visit(ctx.assignment());
        }
        else if(ctx.move() != null){
            res = visit(ctx.move());
        }
        else if(ctx.print() != null){
            res = visit(ctx.print());
        }
        else if(ctx.transfer() != null){
            res = visit(ctx.transfer());
        }
        else if(ctx.ret() != null){
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
        return new TypeNode(ctx.getText());
    }

    @Override
    public Node visitAssignment(AssignmentContext ctx) {
        IdNode id = new IdNode(ctx.ID().getText());
        Node exp = visit(ctx.exp());
        return new AssignmentNode(id,exp);
    }

    /*---------------Simone-------------*/

    @Override
    public Node visitMove(MoveContext ctx) {
        if(ctx.ID().size() < 2){ return null;}
        else{
            IdNode id1 = new IdNode(ctx.ID(0).getText());
            IdNode id2 = new IdNode(ctx.ID(1).getText());
            return new MoveNode(id1,id2);
        }
    }

    @Override
    public Node visitPrint(PrintContext ctx) {
        return new PrintNode(visit(ctx.exp()));
    }

    @Override
    public Node visitTransfer(TransferContext ctx) {
        return new TransferNode(new IdNode(ctx.ID().getText()));
    }

    @Override
    public Node visitRet(RetContext ctx) {
        ReturnNode ret = null;
        if(ctx.children.size() > 1)
            ret = new ReturnNode(visit(ctx.exp()));
        else
            ret = new ReturnNode(null);
        return ret;
    }

    @Override
    public Node visitIte(IteContext ctx) {
        IteNode ret = null;

        ArrayList<Node> listStatement= new ArrayList<>();
        for(StatementContext stc : ctx.statement()){
            listStatement.add(visit(stc));

        }
        ElseStatementContext ctxElse = ctx.elseStatement();
        ArrayList<Node> elseStatement  = null;
            if(ctxElse !=  null){
                elseStatement = new ArrayList<Node>();
                for(StatementContext stc : ctxElse.statement()){
                    elseStatement.add(visit(stc));
                }
            }

        return new IteNode(visit(ctx.exp()),listStatement,elseStatement);
    }

    @Override
    public Node visitCall(CallContext ctx) {
        IdNode id = new IdNode(ctx.ID().get(0).getText());
        ArrayList<Node> params = new ArrayList<>();
        for(ExpContext node: ctx.exp()) {
            params.add(visit(node));
        }
        ArrayList<IdNode> listId = new ArrayList<>();
        List<TerminalNode> tn = ctx.ID();
        tn.remove(0);
        for (TerminalNode node:tn) {
            listId.add(new IdNode(node.getText()));
        }
        return new CallNode(id,params,listId);
    }

    @Override
    public Node visitInitcall(InitcallContext ctx) {
        IdNode id = new IdNode(ctx.ID().getText());
        ArrayList<Node> params = new ArrayList<>();
        for(ExpContext node: ctx.exp()) {
            params.add(visit(node));
        }
        ArrayList<Node> bexp = new ArrayList<>();

        for(BexpContext node : ctx.bexp()){
                bexp.add(visit(node));
        }
        return new InitCallNode(id,params,bexp);
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
