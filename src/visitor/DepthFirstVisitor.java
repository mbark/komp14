package visitor;

import syntaxtree.And;
import syntaxtree.ArrayAssign;
import syntaxtree.ArrayLength;
import syntaxtree.ArrayLookup;
import syntaxtree.Assign;
import syntaxtree.Block;
import syntaxtree.BooleanType;
import syntaxtree.Call;
import syntaxtree.ClassDeclExtends;
import syntaxtree.ClassDeclSimple;
import syntaxtree.ExpList;
import syntaxtree.False;
import syntaxtree.Formal;
import syntaxtree.FormalList;
import syntaxtree.Identifier;
import syntaxtree.IdentifierExp;
import syntaxtree.IdentifierType;
import syntaxtree.If;
import syntaxtree.IntArrayType;
import syntaxtree.IntegerLiteral;
import syntaxtree.IntegerType;
import syntaxtree.LessThan;
import syntaxtree.MainClass;
import syntaxtree.MethodDecl;
import syntaxtree.MethodDeclList;
import syntaxtree.Minus;
import syntaxtree.NewArray;
import syntaxtree.NewObject;
import syntaxtree.Not;
import syntaxtree.Plus;
import syntaxtree.Print;
import syntaxtree.Program;
import syntaxtree.StatementList;
import syntaxtree.This;
import syntaxtree.Times;
import syntaxtree.True;
import syntaxtree.VarDecl;
import syntaxtree.VarDeclList;
import syntaxtree.VoidType;
import syntaxtree.While;

public abstract class DepthFirstVisitor implements Visitor {

    @Override
    public void visit(Program n) {
        n.m.accept(this);
        for (int i = 0; i < n.cl.size(); i++) {
            n.cl.elementAt(i).accept(this);
        }
    }

    @Override
    public void visit(MainClass n) {
        n.i1.accept(this);
        n.i2.accept(this);
        visit(n.vl);
        n.s.accept(this);
    }

    @Override
    public void visit(ClassDeclSimple n) {
        n.i.accept(this);
        visit(n.vl);
        visit(n.ml);
    }

    @Override
    public void visit(ClassDeclExtends n) {
        n.i.accept(this);
        n.j.accept(this);
        visit(n.vl);
        visit(n.ml);
    }

    @Override
    public void visit(VarDecl n) {
        n.t.accept(this);
        n.i.accept(this);
    }

    @Override
    public void visit(MethodDecl n) {
        n.t.accept(this);
        n.i.accept(this);
        visit(n.fl);
        visit(n.vl);
        visit(n.sl);
        n.e.accept(this);
    }

    @Override
    public void visit(Formal n) {
        n.t.accept(this);
        n.i.accept(this);
    }

    @Override
    public void visit(IntArrayType n) {
        // No-op
    }

    @Override
    public void visit(BooleanType n) {
        // No-op
    }

    @Override
    public void visit(IntegerType n) {
        // No-op
    }

    @Override
    public void visit(VoidType n) {
        // No-op
    }

    @Override
    public void visit(IdentifierType n) {
        // No-op
    }

    @Override
    public void visit(Block n) {
        visit(n.sl);
    }

    @Override
    public void visit(If n) {
        n.e.accept(this);
        n.s1.accept(this);
        n.s2.accept(this);
    }

    @Override
    public void visit(While n) {
        n.e.accept(this);
        n.s.accept(this);
    }

    @Override
    public void visit(Print n) {
        n.e.accept(this);
    }

    @Override
    public void visit(Assign n) {
        n.i.accept(this);
        n.e.accept(this);
    }

    @Override
    public void visit(ArrayAssign n) {
        n.i.accept(this);
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override
    public void visit(And n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override
    public void visit(LessThan n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override
    public void visit(Plus n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override
    public void visit(Minus n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override
    public void visit(Times n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override
    public void visit(ArrayLookup n) {
        n.e1.accept(this);
        n.e2.accept(this);
    }

    @Override
    public void visit(ArrayLength n) {
        n.e.accept(this);
    }

    @Override
    public void visit(Call n) {
        n.e.accept(this);
        n.i.accept(this);
        visit(n.el);
    }

    @Override
    public void visit(IntegerLiteral n) {
        // No-op
    }

    @Override
    public void visit(True n) {
        // No-op
    }

    @Override
    public void visit(False n) {
        // No-op
    }

    @Override
    public void visit(IdentifierExp n) {
        // No-op
    }

    @Override
    public void visit(This n) {
        // No-op
    }

    @Override
    public void visit(NewArray n) {
        n.e.accept(this);
    }

    @Override
    public void visit(NewObject n) {
        n.i.accept(this);
    }

    @Override
    public void visit(Not n) {
        n.e.accept(this);
    }

    @Override
    public void visit(Identifier n) {
        // No-op
    }

    private void visit(MethodDeclList ml) {
        for (int i = 0; i < ml.size(); i++) {
            ml.elementAt(i).accept(this);
        }
    }

    private void visit(VarDeclList vl) {
        for (int i = 0; i < vl.size(); i++) {
            vl.elementAt(i).accept(this);
        }
    }

    private void visit(ExpList el) {
        for (int i = 0; i < el.size(); i++) {
            el.elementAt(i).accept(this);
        }
    }

    private void visit(StatementList sl) {
        for (int i = 0; i < sl.size(); i++) {
            sl.elementAt(i).accept(this);
        }
    }

    private void visit(FormalList fl) {
        for (int i = 0; i < fl.size(); i++) {
            fl.elementAt(i).accept(this);
        }
    }
}
