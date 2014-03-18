package symbol;

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
import visitor.DepthFirstVisitor;
import error.ErrorMsg;

public class TypeCheckPhaseOneVisitor extends DepthFirstVisitor {

    private ProgramTable currProgram;
    private MethodTable currMethod;
    private ClassTable currClass;
    private ErrorMsg error;

    public TypeCheckPhaseOneVisitor() {
        currProgram = null;
        currMethod = null;
        currClass = null;
    }

    @Override
    public void visit(Program n) {
        currProgram = new ProgramTable();
        super.visit(n);
        System.out.println(currProgram);
    }

    @Override
    public void visit(MainClass n) {
        currClass = new ClassTable(convertToSymbol(n.i1));
        currMethod = new MethodTable(Symbol.symbol("main"), new VoidType());
        // FIXME: String?
        currMethod.putParam(convertToSymbol(n.i2), new VoidType());

        super.visit(n);

        currClass.put(currMethod.getId(), currMethod);
        currMethod = null;
        currProgram.put(currClass.getId(), currClass);
    }

    @Override
    public void visit(ClassDeclSimple n) {
        currClass = new ClassTable(convertToSymbol(n.i));
        super.visit(n);
        currProgram.put(currClass.getId(), currClass);
    }

    @Override
    public void visit(ClassDeclExtends n) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public void visit(VarDecl n) {
        Type t = n.t;
        Symbol id = convertToSymbol(n.i);
        if (currMethod == null) {
            if (!currClass.put(id, t)) {
                // TODO: tell where the symbol was identified
                error.complain(id + " is already defined");
            }
        } else if (!currMethod.putLocal(id, t)) {
            // TODO: tell where the symbol was identified
            error.complain(id + " is already defined");
        }
    }

    @Override
    public void visit(MethodDecl n) {
        currMethod = new MethodTable(convertToSymbol(n.i), n.t);
        super.visit(n);
        currClass.put(currMethod.getId(), currMethod);
        currMethod = null;
    }

    @Override
    public void visit(Formal n) {
        currMethod.putParam(convertToSymbol(n.i), n.t);
        super.visit(n);
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
    public void visit(IdentifierType n) {
        // No-op
    }

    @Override
    public void visit(Block n) {
        // No-op
    }

    @Override
    public void visit(If n) {
        // No-op
    }

    @Override
    public void visit(While n) {
        // No-op
    }

    @Override
    public void visit(Print n) {
        // No-op
    }

    @Override
    public void visit(Assign n) {
        // No-op
    }

    @Override
    public void visit(ArrayAssign n) {
        // No-op
    }

    @Override
    public void visit(And n) {
        // No-op
    }

    @Override
    public void visit(LessThan n) {
        // No-op
    }

    @Override
    public void visit(Plus n) {
        // No-op
    }

    @Override
    public void visit(Minus n) {
        // No-op
    }

    @Override
    public void visit(Times n) {
        // No-op
    }

    @Override
    public void visit(ArrayLookup n) {
        // No-op
    }

    @Override
    public void visit(ArrayLength n) {
        // No-op
    }

    @Override
    public void visit(Call n) {
        // No-op
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
        // No-op
    }

    @Override
    public void visit(NewObject n) {
        // No-op
    }

    @Override
    public void visit(Not n) {
        // No-op
    }

    @Override
    public void visit(Identifier n) {
        // No-op
    }

    private static Symbol convertToSymbol(Identifier i) {
        return Symbol.symbol(i.toString());
    }

}
