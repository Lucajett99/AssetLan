package ast;

import ast.ExpNodes.*;
import gen.AssetLanBaseVisitor;
import gen.AssetLanParser.*;

import org.antlr.v4.runtime.tree.ErrorNode;
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
        Node type = null;
        if (ctx.type() != null)
             type = visit(ctx.type());
        IdNode id = new IdNode(ctx.ID().getText());
        AdecNode adec = null;
        ArrayList<DecNode> dec = new ArrayList<DecNode>();
        ArrayList<IdNode> idp = new ArrayList<IdNode>();
        ArrayList<TypeNode> typep = new ArrayList<TypeNode>();
        ArrayList<Node> statement = new ArrayList<Node>();
        DecpNode decp = null;
        if(!ctx.decp().isEmpty())decp = new DecpNode((DecNode) visit(ctx.decp()));
        int i = 0;
        for (DecContext dc : ctx.dec()) {
            idp.add((IdNode) visit(dc.ID(i)));
            typep.add((TypeNode) visit(dc.type(i)));
            i++;
        }
        if(ctx.adec() != null) {
            adec  = (AdecNode) visit(ctx.adec());
        }
        for (StatementContext sc : ctx.statement()) {
            statement.add(visit(sc));
        }
        return new FunctionNode(type, id, decp,new DecNode(typep, idp), adec, statement);

    }

    @Override
    public Node visitDec(DecContext ctx) {
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
        if(!ctx.exp().isEmpty()){
            ret = new ReturnNode(visit(ctx.exp()));
        }
        return ret;
    }

    @Override
    public Node visitIte(IteContext ctx) {
        IteNode ret = null;
        if(ctx.statement().size()==1){ //there's one only statement IF(cond)THEN{statement} ELSE null
            ret = new IteNode(visit(ctx.exp()),visit(ctx.statement().get(0)),null);
        }else if(ctx.statement().size()==2){ //there are two statement IF(cond)THEN{statement} ELSE {statement}
            ret = new IteNode(visit(ctx.exp()),visit(ctx.statement().get(0)),visit(ctx.statement().get(1)));
        }
        return ret;
    }

    @Override
    public Node visitCall(CallContext ctx) {
        IdNode id = new IdNode(ctx.getText());
        ArrayList<Node> params = new ArrayList<>();
        for(ExpContext node: ctx.exp()) {
            params.add(visit(node));
        }
        ArrayList<IdNode> listId = new ArrayList<>();
        for (TerminalNode node: ctx.ID()) {
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
