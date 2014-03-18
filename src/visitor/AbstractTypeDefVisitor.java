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
import syntaxtree.This;
import syntaxtree.Times;
import syntaxtree.True;
import syntaxtree.VarDecl;
import syntaxtree.VoidType;
import syntaxtree.While;

public abstract class AbstractTypeDefVisitor extends DepthFirstVisitor {

    @Override
    public void visit(Program n) {
        super.visit(n);
    }

    @Override
    public void visit(MainClass n) {
        super.visit(n);
    }

    @Override
    public void visit(ClassDeclSimple n) {
        super.visit(n);
    }

    @Override
    public void visit(ClassDeclExtends n) {
        super.visit(n);
    }

    @Override
    public void visit(VarDecl n) {
        super.visit(n);
    }

    @Override
    public void visit(MethodDecl n) {
        super.visit(n);
    }

    @Override
    public void visit(Formal n) {
        super.visit(n);
    }

    @Override
    public void visit(IntArrayType n) {
    }

    @Override
    public void visit(BooleanType n) {
    }

    @Override
    public void visit(IntegerType n) {
    }

    @Override
    public void visit(VoidType n) {
    }

    @Override
    public void visit(IdentifierType n) {
    }

    @Override
    public void visit(Block n) {
    }

    @Override
    public void visit(If n) {
    }

    @Override
    public void visit(While n) {
    }

    @Override
    public void visit(Print n) {
    }

    @Override
    public void visit(Assign n) {
    }

    @Override
    public void visit(ArrayAssign n) {
    }

    @Override
    public void visit(And n) {
    }

    @Override
    public void visit(LessThan n) {
    }

    @Override
    public void visit(Plus n) {
    }

    @Override
    public void visit(Minus n) {
    }

    @Override
    public void visit(Times n) {
    }

    @Override
    public void visit(ArrayLookup n) {
    }

    @Override
    public void visit(ArrayLength n) {
    }

    @Override
    public void visit(Call n) {
    }

    @Override
    public void visit(IntegerLiteral n) {
    }

    @Override
    public void visit(True n) {
    }

    @Override
    public void visit(False n) {
    }

    @Override
    public void visit(IdentifierExp n) {
    }

    @Override
    public void visit(This n) {
    }

    @Override
    public void visit(NewArray n) {
    }

    @Override
    public void visit(NewObject n) {
    }

    @Override
    public void visit(Not n) {
    }

    @Override
    public void visit(Identifier n) {
    }

}
