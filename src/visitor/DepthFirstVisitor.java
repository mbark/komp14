package visitor;

import syntaxtree.And;
import syntaxtree.ArrayAssign;
import syntaxtree.ArrayLength;
import syntaxtree.ArrayLookup;
import syntaxtree.Assign;
import syntaxtree.Block;
import syntaxtree.BooleanType;
import syntaxtree.Call;
import syntaxtree.ClassDecl;
import syntaxtree.ClassDeclExtends;
import syntaxtree.ClassDeclSimple;
import syntaxtree.Exp;
import syntaxtree.False;
import syntaxtree.Formal;
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
import syntaxtree.Minus;
import syntaxtree.NewArray;
import syntaxtree.NewObject;
import syntaxtree.Not;
import syntaxtree.Plus;
import syntaxtree.Print;
import syntaxtree.Program;
import syntaxtree.Statement;
import syntaxtree.This;
import syntaxtree.Times;
import syntaxtree.True;
import syntaxtree.VarDecl;
import syntaxtree.While;

public class DepthFirstVisitor implements Visitor {

    @Override
    public void visit(Program n) {
        visit(n.m);
        for (int i = 0; i < n.cl.size(); i++) {
            ClassDecl cd = n.cl.elementAt(i);
            if (cd instanceof ClassDeclSimple) {
                visit((ClassDeclSimple) cd);
            } else if (cd instanceof ClassDeclExtends) {
                visit((ClassDeclExtends) cd);
            }
        }
    }

    @Override
    public void visit(MainClass n) {
        visit(n.i1);
        visit(n.i2);
        visit(n.s);
    }

    @Override
    public void visit(ClassDeclSimple n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ClassDeclExtends n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(VarDecl n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(MethodDecl n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(Formal n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(IntArrayType n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(BooleanType n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(IntegerType n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(IdentifierType n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(Block n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(If n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(While n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(Print n) {
        visit(n.e);
    }

    @Override
    public void visit(Assign n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ArrayAssign n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(And n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(LessThan n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(Plus n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(Minus n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(Times n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ArrayLookup n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(ArrayLength n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(Call n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(IntegerLiteral n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(True n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(False n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(IdentifierExp n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(This n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(NewArray n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(NewObject n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(Not n) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visit(Identifier n) {
        // TODO Auto-generated method stub

    }

    private void visit(Exp e) {
        if (e instanceof IntegerLiteral) {
            visit((IntegerLiteral) e);
        }
    }

    private void visit(Statement s) {
        if (s instanceof Print) {
            visit((Print) s);
        }
    }

}
