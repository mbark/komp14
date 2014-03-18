package symbol;

import syntaxtree.ClassDeclExtends;
import syntaxtree.ClassDeclSimple;
import syntaxtree.Identifier;
import syntaxtree.MainClass;
import syntaxtree.MethodDecl;
import syntaxtree.Program;
import syntaxtree.Type;
import syntaxtree.VarDecl;
import syntaxtree.VoidType;
import visitor.AbstractTypeDefVisitor;
import error.ErrorMsg;

public class TypeDefVisitor extends AbstractTypeDefVisitor {

    private ProgramTable currProgram;
    private MethodTable currMethod;
    private ClassTable currClass;
    private ErrorMsg error;

    public TypeDefVisitor() {
        currProgram = null;
        currMethod = null;
        currClass = null;
        error = new ErrorMsg(System.err);
    }

    @Override
    public void visit(Program n) {
        currProgram = new ProgramTable();
        super.visit(n);
        if (!error.hasAnyErrors()) {
            System.out.println(currProgram);
        } else {
            System.exit(1);
        }
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

    private static Symbol convertToSymbol(Identifier i) {
        return Symbol.symbol(i.toString());
    }

}
