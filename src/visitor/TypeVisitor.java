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

public interface TypeVisitor {

    // MainClass m;
    // ClassDeclList cl;
    public Type visit(Program n);

    // Identifier i1,i2;
    // Statement s;
    public Type visit(MainClass n);

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclSimple n);

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Type visit(ClassDeclExtends n);

    // Type t;
    // Identifier i;
    public Type visit(VarDecl n);

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public Type visit(MethodDecl n);

    // Type t;
    // Identifier i;
    public Type visit(Formal n);

    public Type visit(IntArrayType n);

    public Type visit(BooleanType n);

    public Type visit(IntegerType n);

    public Type visit(VoidType n);

    // String s;
    public Type visit(IdentifierType n);

    // StatementList sl;
    public Type visit(Block n);

    // Exp e;
    // Statement s1,s2;
    public Type visit(If n);

    // Exp e;
    // Statement s;
    public Type visit(While n);

    // Exp e;
    public Type visit(Print n);

    // Identifier i;
    // Exp e;
    public Type visit(Assign n);

    // Identifier i;
    // Exp e1,e2;
    public Type visit(ArrayAssign n);

    // Exp e1,e2;
    public Type visit(And n);

    // Exp e1,e2;
    public Type visit(LessThan n);

    // Exp e1,e2;
    public Type visit(Plus n);

    // Exp e1,e2;
    public Type visit(Minus n);

    // Exp e1,e2;
    public Type visit(Times n);

    // Exp e1,e2;
    public Type visit(ArrayLookup n);

    // Exp e;
    public Type visit(ArrayLength n);

    // Exp e;
    // Identifier i;
    // ExpList el;
    public Type visit(Call n);

    // int i;
    public Type visit(IntegerLiteral n);

    public Type visit(True n);

    public Type visit(False n);

    // String s;
    public Type visit(IdentifierExp n);

    public Type visit(This n);

    // Exp e;
    public Type visit(NewArray n);

    // Identifier i;
    public Type visit(NewObject n);

    // Exp e;
    public Type visit(Not n);

    // String s;
    public Type visit(Identifier n);

}
