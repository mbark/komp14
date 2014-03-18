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
import syntaxtree.Type;
import syntaxtree.VarDecl;
import syntaxtree.VoidType;
import syntaxtree.While;
import error.ErrorMsg;

public class TypeCheckVisitor implements TypeVisitor {

    private ErrorMsg error;

    public TypeCheckVisitor() {
        error = new ErrorMsg(System.err);
    }

    @Override
    public Type visit(Program n) {
        return null;
    }

    @Override
    public Type visit(MainClass n) {
        return null;
    }

    @Override
    public Type visit(ClassDeclSimple n) {
        return null;
    }

    @Override
    public Type visit(ClassDeclExtends n) {
        return null;
    }

    @Override
    public Type visit(VarDecl n) {
        return null;
    }

    @Override
    public Type visit(MethodDecl n) {
        return null;
    }

    @Override
    public Type visit(Formal n) {
        return null;
    }

    @Override
    public Type visit(IntArrayType n) {
        return null;
    }

    @Override
    public Type visit(BooleanType n) {
        return null;
    }

    @Override
    public Type visit(IntegerType n) {
        return null;
    }

    @Override
    public Type visit(VoidType n) {
        return null;
    }

    @Override
    public Type visit(IdentifierType n) {
        return null;
    }

    @Override
    public Type visit(Block n) {
        return null;
    }

    @Override
    public Type visit(If n) {
        return null;
    }

    @Override
    public Type visit(While n) {
        return null;
    }

    @Override
    public Type visit(Print n) {
        return null;
    }

    @Override
    public Type visit(Assign n) {
        return null;
    }

    @Override
    public Type visit(ArrayAssign n) {
        return null;
    }

    @Override
    public Type visit(And n) {
        return null;
    }

    @Override
    public Type visit(LessThan n) {
        return null;
    }

    @Override
    public Type visit(Plus n) {
        if (!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of LessThan must be of type integer");
        }
        if (!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of LessThan must be of type integer");
        }
        return new IntegerType();
    }

    @Override
    public Type visit(Minus n) {
        return null;
    }

    @Override
    public Type visit(Times n) {
        return null;
    }

    @Override
    public Type visit(ArrayLookup n) {
        return null;
    }

    @Override
    public Type visit(ArrayLength n) {
        return null;
    }

    @Override
    public Type visit(Call n) {
        return null;
    }

    @Override
    public Type visit(IntegerLiteral n) {
        return null;
    }

    @Override
    public Type visit(True n) {
        return null;
    }

    @Override
    public Type visit(False n) {
        return null;
    }

    @Override
    public Type visit(IdentifierExp n) {
        return null;
    }

    @Override
    public Type visit(This n) {
        return null;
    }

    @Override
    public Type visit(NewArray n) {
        return null;
    }

    @Override
    public Type visit(NewObject n) {
        return null;
    }

    @Override
    public Type visit(Not n) {
        return null;
    }

    @Override
    public Type visit(Identifier n) {
        return null;
    }

}
