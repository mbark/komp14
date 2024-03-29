package visitor;

import symbol.ClassTable;
import symbol.MethodTable;
import symbol.ProgramTable;
import symbol.Symbol;
import syntaxtree.ClassDeclExtends;
import syntaxtree.ClassDeclSimple;
import syntaxtree.Formal;
import syntaxtree.Identifier;
import syntaxtree.MainClass;
import syntaxtree.MethodDecl;
import syntaxtree.Program;
import syntaxtree.Type;
import syntaxtree.VarDecl;
import syntaxtree.VoidType;
import error.ErrorMsg;

public class TypeDefVisitor extends AbstractTypeDefVisitor {

    private ProgramTable currProgram;
    private MethodTable currMethod;
    private ClassTable currClass;
    private ErrorMsg error;

    public TypeDefVisitor(ErrorMsg error) {
        currProgram = null;
        currMethod = null;
        currClass = null;
        this.error = error;
    }

    public ProgramTable getProgramTable() {
        return currProgram;
    }

    @Override
    public void visit(Program n) {
        currProgram = new ProgramTable();
        super.visit(n);
    }

    @Override
    public void visit(MainClass n) {
        currClass = new ClassTable(convertToSymbol(n.i1));
        currMethod = new MethodTable(Symbol.symbol("main"), new VoidType());
        // We can ignore the String[] args param, its use is undefined

        super.visit(n);

        addMethodToClass();
        addClassToProgram();
    }

    @Override
    public void visit(ClassDeclSimple n) {
        currClass = new ClassTable(convertToSymbol(n.i));

        super.visit(n);

        addClassToProgram();
    }

    @Override
    public void visit(ClassDeclExtends n) {
        currClass = new ClassTable(convertToSymbol(n.i));
        currClass.setSuperClass(convertToSymbol(n.j));

        super.visit(n);

        addClassToProgram();
    }

    @Override
    public void visit(VarDecl n) {
        Type t = n.t;
        Symbol id = convertToSymbol(n.i);
        if (currMethod == null) {
            addFieldToClass(id, t);
        } else {
            addLocalToMethod(id, t);
        }
    }

    @Override
    public void visit(MethodDecl n) {
        currMethod = new MethodTable(convertToSymbol(n.i), n.t);

        super.visit(n);

        addMethodToClass();
    }

    @Override
    public void visit(Formal n) {
        addParamToMethod(convertToSymbol(n.i), n.t);

        super.visit(n);
    }

    private static Symbol convertToSymbol(Identifier i) {
        return Symbol.symbol(i.toString());
    }

    private void addClassToProgram() {
        if (!currProgram.put(currClass.getId(), currClass)) {
            error.complain("Class" + currClass.getId() + " is already defined");
        }
        currClass = null;
    }

    private void addMethodToClass() {
        if (!currClass.put(currMethod.getId(), currMethod)) {
            error.complain("Method " + currMethod.getId()
                    + " is already defined for class " + currClass.getId());
        }
        currMethod = null;
    }

    private void addParamToMethod(Symbol symbol, Type type) {
        if (!currMethod.putParam(symbol, type)) {
            error.complain("Param " + currMethod.getId()
                    + " is already defined for method " + currMethod.getId());
        }
    }

    private void addLocalToMethod(Symbol id, Type t) {
        if (!currMethod.putLocal(id, t)) {
            error.complain("Local " + id + " is already defined for method "
                    + currMethod.getId());
        }
    }

    private void addFieldToClass(Symbol id, Type t) {
        if (!currClass.put(id, t)) {
            error.complain("Field " + id + " is already defined for class "
                    + currClass.getId());
        }
    }

}
