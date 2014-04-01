package visitor;

import symbol.ClassTable;
import symbol.MethodTable;
import symbol.ProgramTable;
import symbol.Symbol;
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
import temp.Label;
import tree.AbstractExp;
import tree.CALL;
import tree.ExpList;
import tree.LABEL;
import tree.Stm;

public class TreeBuilderVisitor implements TreeVisitor {

    private ProgramTable currProgram;
    private ClassTable currClass;
    private MethodTable currMethod;

    public TreeBuilderVisitor(ProgramTable programTable) {
        currProgram = programTable;
        currClass = null;
        currMethod = null;
    }

    @Override
    public Stm visit(Program n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stm visit(MainClass n) {
        currClass = currProgram.get(convertToSymbol(n.i1));
        currMethod = currClass.getMethod(Symbol.symbol("main"));
        // TODO Auto-generated method stub
        currMethod = null;
        currClass = null;
        return null;
    }

    @Override
    public Stm visit(ClassDeclSimple n) {
        currClass = currProgram.get(convertToSymbol(n.i));
        // TODO Auto-generated method stub
        currClass = null;
        return null;
    }

    @Override
    public Stm visit(ClassDeclExtends n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stm visit(VarDecl n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stm visit(MethodDecl n) {
        currMethod = currClass.getMethod(convertToSymbol(n.i));
        // TODO Auto-generated method stub
        currMethod = null;
        return null;
    }

    @Override
    public AbstractExp visit(Formal n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(IntArrayType n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(BooleanType n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(IntegerType n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(VoidType n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(IdentifierType n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stm visit(Block n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stm visit(If n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stm visit(While n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stm visit(Print n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stm visit(Assign n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stm visit(ArrayAssign n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(And n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(LessThan n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(Plus n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(Minus n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(Times n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(ArrayLookup n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(ArrayLength n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(Call n) {
        AbstractExp callee = n.e.accept(this);
        Label l = new Label(n.i.s);
        AbstractExp ae = null;
        return new CALL(ae, visit(n.el));
    }

    @Override
    public AbstractExp visit(IntegerLiteral n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(True n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(False n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(IdentifierExp n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(This n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(NewArray n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(NewObject n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(Not n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(Identifier n) {
        // TODO Auto-generated method stub
        return null;
    }

    private static Symbol convertToSymbol(Identifier i) {
        return Symbol.symbol(i.toString());
    }

    private ExpList visit(syntaxtree.ExpList el) {
        tree.ExpList telHead = null;
        tree.ExpList telTail = null;

        for (int i = 0; i < el.size(); i++) {
            AbstractExp ae = el.elementAt(i).accept(this);
            if (telHead == null) {
                telHead = new tree.ExpList(ae);
                telTail = telHead;
            } else {
                ExpList newTelTail = new tree.ExpList(ae);
                telTail.tail = newTelTail;
                telTail = newTelTail;
            }
        }

        return telHead;
    }

}
