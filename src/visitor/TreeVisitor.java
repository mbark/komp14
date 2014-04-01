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
import tree.AbstractExp;
import tree.Stm;

public interface TreeVisitor {

    // MainClass m;
    // ClassDeclList cl;
    public Stm visit(Program n);

    // Identifier i1,i2;
    // Statement s;
    public Stm visit(MainClass n);

    // Identifier i;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Stm visit(ClassDeclSimple n);

    // Identifier i;
    // Identifier j;
    // VarDeclList vl;
    // MethodDeclList ml;
    public Stm visit(ClassDeclExtends n);

    // Stm t;
    // Identifier i;
    public AbstractExp visit(VarDecl n);

    // Stm t;
    // Identifier i;
    // FormalList fl;
    // VarDeclList vl;
    // StatementList sl;
    // Exp e;
    public Stm visit(MethodDecl n);

    // Stm t;
    // Identifier i;
    public AbstractExp visit(Formal n);

    public AbstractExp visit(IntArrayType n);

    public AbstractExp visit(BooleanType n);

    public AbstractExp visit(IntegerType n);

    public AbstractExp visit(VoidType n);

    // String s;
    public AbstractExp visit(IdentifierType n);

    // StatementList sl;
    public Stm visit(Block n);

    // Exp e;
    // Statement s1,s2;
    public Stm visit(If n);

    // Exp e;
    // Statement s;
    public Stm visit(While n);

    // Exp e;
    public Stm visit(Print n);

    // Identifier i;
    // Exp e;
    public Stm visit(Assign n);

    // Identifier i;
    // Exp e1,e2;
    public Stm visit(ArrayAssign n);

    // Exp e1,e2;
    public AbstractExp visit(And n);

    // Exp e1,e2;
    public AbstractExp visit(LessThan n);

    // Exp e1,e2;
    public AbstractExp visit(Plus n);

    // Exp e1,e2;
    public AbstractExp visit(Minus n);

    // Exp e1,e2;
    public AbstractExp visit(Times n);

    // Exp e1,e2;
    public AbstractExp visit(ArrayLookup n);

    // Exp e;
    public AbstractExp visit(ArrayLength n);

    // Exp e;
    // Identifier i;
    // ExpList el;
    public AbstractExp visit(Call n);

    // int i;
    public AbstractExp visit(IntegerLiteral n);

    public AbstractExp visit(True n);

    public AbstractExp visit(False n);

    // String s;
    public AbstractExp visit(IdentifierExp n);

    public AbstractExp visit(This n);

    // Exp e;
    public AbstractExp visit(NewArray n);

    // Identifier i;
    public AbstractExp visit(NewObject n);

    // Exp e;
    public AbstractExp visit(Not n);

    // String s;
    public AbstractExp visit(Identifier n);
}
