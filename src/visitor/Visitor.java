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
import syntaxtree.While;

public interface Visitor {

    // MainClass m;
    // ClassDeclList cl;
    public void visit(Program n);

    // Identifier i1,i2;
    // Statement s;
    public void visit(MainClass n);

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclSimple n);

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public void visit(ClassDeclExtends n);

    // Type t;
    // Identifier i;
    public void visit(VarDecl n);

    // Type t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public void visit(MethodDecl n);

    // Type t;
    // Identifier i;
    public void visit(Formal n);

    public void visit(IntArrayType n);

    public void visit(BooleanType n);

    public void visit(IntegerType n);

    // String s;
    public void visit(IdentifierType n);

    // StatementList sl;
    public void visit(Block n);

    // Exp e;
    // Statement s1,s2;
    public void visit(If n);

    // Exp e;
    // Statement s;
    public void visit(While n);

    // Exp e;
    public void visit(Print n);

    // Identifier i;
    // Exp e;
    public void visit(Assign n);

    // Identifier i;
    // Exp e1,e2;
    public void visit(ArrayAssign n);

    // Exp e1,e2;
    public void visit(And n);

    // Exp e1,e2;
    public void visit(LessThan n);

    // Exp e1,e2;
    public void visit(Plus n);

    // Exp e1,e2;
    public void visit(Minus n);

    // Exp e1,e2;
    public void visit(Times n);

    // Exp e1,e2;
    public void visit(ArrayLookup n);

    // Exp e;
    public void visit(ArrayLength n);

    // Exp e;
    // Identifier i;
    // ExpList el;
    public void visit(Call n);

    // int i;
    public void visit(IntegerLiteral n);

    public void visit(True n);

    public void visit(False n);

    // String s;
    public void visit(IdentifierExp n);

    public void visit(This n);

    // Exp e;
    public void visit(NewArray n);

    // Identifier i;
    public void visit(NewObject n);

    // Exp e;
    public void visit(Not n);

    // String s;
    public void visit(Identifier n);

}
