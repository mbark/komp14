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
import syntaxtree.Type;
import syntaxtree.VarDecl;
import syntaxtree.VoidType;
import syntaxtree.While;
import error.ErrorMsg;

public class TypeCheckVisitor implements TypeVisitor {

    private ProgramTable currProgram;
    private ClassTable currClass;
    private MethodTable currMethod;
    private ErrorMsg error;

    public TypeCheckVisitor(ProgramTable programTable, ErrorMsg error) {
        this.error = error;
        currProgram = programTable;
    }

    private Type getTypeOfVariable(Symbol var) {
        Type type = null;
        if (currMethod != null) {
            type = currMethod.getLocal(var);
            if (type == null) {
                type = currMethod.getParam(var);
            }
        }

        if (type == null && currClass != null) {
            type = currClass.getField(var);
        }

        return type;
    }

    private String getContext() {
        if (currClass == null) {
            return "no class";
        } else if (currMethod == null) {
            return "class " + currClass.getId();
        } else {
            return "class " + currClass.getId() + " and method "
                    + currMethod.getId();
        }
    }

    @Override
    public Type visit(Program n) {
        n.m.accept(this);
        for (int i = 0; i < n.cl.size(); i++) {
            n.cl.elementAt(i).accept(this);
        }

        // TODO: other type?
        return new VoidType();
    }

    @Override
    public Type visit(MainClass n) {
        currClass = currProgram.get(convertToSymbol(n.i1));
        currMethod = currClass.getMethod(Symbol.symbol("main"));

        n.s.accept(this);
        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.elementAt(i).accept(this);
        }

        currMethod = null;
        currClass = null;
        // TODO: other type?
        return new VoidType();
    }

    @Override
    public Type visit(ClassDeclSimple n) {
        currClass = currProgram.get(convertToSymbol(n.i));

        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.ml.size(); i++) {
            n.ml.elementAt(i).accept(this);
        }

        currClass = null;
        // TODO: other type?
        return new VoidType();
    }

    @Override
    public Type visit(ClassDeclExtends n) {
        // TODO: fix
        return null;
    }

    @Override
    public Type visit(VarDecl n) {
        return n.t;
    }

    @Override
    public Type visit(MethodDecl n) {
        currMethod = currClass.getMethod(convertToSymbol(n.i));

        if (currClass == null) {
            error.complain("No method with name " + n.i + " for class "
                    + currClass.getId());
            // TODO: other type?
            return new VoidType();
        }

        for (int i = 0; i < n.fl.size(); i++) {
            n.fl.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.vl.size(); i++) {
            n.vl.elementAt(i).accept(this);
        }

        for (int i = 0; i < n.sl.size(); i++) {
            n.sl.elementAt(i).accept(this);
        }

        Type returnType = n.e.accept(this);

        if (!returnType.equals(currMethod.getReturnType())) {
            error.complain("Invalid return type for method "
                    + currMethod.getId() + ", expected: "
                    + currMethod.getReturnType() + " actual: " + returnType);
        }

        currMethod = null;
        return new VoidType();
    }

    @Override
    public Type visit(Formal n) {
        return n.t;
    }

    @Override
    public Type visit(IntArrayType n) {
        return n;
    }

    @Override
    public Type visit(BooleanType n) {
        return n;
    }

    @Override
    public Type visit(IntegerType n) {
        return n;
    }

    @Override
    public Type visit(VoidType n) {
        return n;
    }

    @Override
    public Type visit(IdentifierType n) {
        return n;
    }

    @Override
    public Type visit(Block n) {
        for (int i = 0; i < n.sl.size(); i++) {
            n.sl.elementAt(i).accept(this);
        }
        return new VoidType();
    }

    @Override
    public Type visit(If n) {
        if (!(n.e.accept(this) instanceof BooleanType)) {
            error.complain("Expression in While must be of type boolean");
        }
        n.s1.accept(this);
        n.s2.accept(this);
        return new VoidType();
    }

    @Override
    public Type visit(While n) {
        if (!(n.e.accept(this) instanceof BooleanType)) {
            error.complain("Expression in While must be of type boolean");
        }
        n.s.accept(this);
        return new VoidType();
    }

    @Override
    public Type visit(Print n) {
        Type t = n.e.accept(this);
        if(t instanceof IntArrayType || t instanceof IdentifierType) {
            error.complain("Invalid type used for print " + t);
        }
        return new VoidType();
    }

    @Override
    public Type visit(Assign n) {
        final Type leftType = getTypeOfVariable(convertToSymbol(n.i));
        if (leftType == null) {
            error.complain("Variable " + n.i + " in " + getContext()
                    + " has no type declared");
            return new VoidType();
        }
        final Type rightType = n.e.accept(this);

        if (!leftType.equals(rightType)) {
            error.complain("Expression must be of same type as var, expected: "
                    + leftType + " actual: " + rightType);
        }
        return new VoidType();
    }

    @Override
    public Type visit(ArrayAssign n) {
        Type t = getTypeOfVariable(convertToSymbol(n.i));
        if (!(t instanceof IntArrayType)) {
            error.complain("Variable in ArrayAssign must be of type "
                    + IntArrayType.class.getSimpleName() + " but is of type "
                    + t);
        }
        if (!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Index in ArrayAssign must be of type integer");
        }
        if (!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Value of ArrayAssign must be of type integer");
        }

        return new VoidType();
    }

    @Override
    public Type visit(And n) {
        if (!(n.e1.accept(this) instanceof BooleanType)) {
            error.complain("Left side of " + n.getClass().getSimpleName()
                    + " must be of type boolean");
        }
        if (!(n.e2.accept(this) instanceof BooleanType)) {
            error.complain("Right side of " + n.getClass().getSimpleName()
                    + " must be of type boolean");
        }
        return new BooleanType();
    }

    @Override
    public Type visit(LessThan n) {
        if (!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of " + n.getClass().getSimpleName()
                    + " must be of type integer");
        }
        if (!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of " + n.getClass().getSimpleName()
                    + " must be of type integer");
        }
        return new BooleanType();
    }

    @Override
    public Type visit(Plus n) {
        if (!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of " + n.getClass().getSimpleName()
                    + " must be of type integer");
        }
        if (!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of " + n.getClass().getSimpleName()
                    + " must be of type integer");
        }
        return new IntegerType();
    }

    @Override
    public Type visit(Minus n) {
        if (!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of " + n.getClass().getSimpleName()
                    + " must be of type integer");
        }
        if (!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of " + n.getClass().getSimpleName()
                    + " must be of type integer");
        }
        return new IntegerType();
    }

    @Override
    public Type visit(Times n) {
        if (!(n.e1.accept(this) instanceof IntegerType)) {
            error.complain("Left side of " + n.getClass().getSimpleName()
                    + " must be of type integer");
        }
        if (!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Right side of " + n.getClass().getSimpleName()
                    + " must be of type integer");
        }
        return new IntegerType();
    }

    @Override
    public Type visit(ArrayLookup n) {
        Type t = n.e1.accept(this);
        if (!(t instanceof IntArrayType)) {
            complainAboutIcorrectVariableTypes(n, IntArrayType.class, t);
        }

        if (!(n.e2.accept(this) instanceof IntegerType)) {
            error.complain("Index of ArrayLookup must be of type integer");
        }

        return new IntegerType();
    }

    @Override
    public Type visit(ArrayLength n) {
        if (!(n.e.accept(this) instanceof IntArrayType)) {
            error.complain("Variable in ArrayLength must be of type integer array");
        }
        return new IntegerType();
    }

    @Override
    public Type visit(Call n) {
        // Check type of expression
        Type expType = n.e.accept(this);
        if (!(expType instanceof IdentifierType)) {
            error.complain("Variable in Call must be of type identifier type");
        }

        // Ensure the type is defined (as a class)
        IdentifierType idType = (IdentifierType) expType;
        Type returnType = new VoidType(); // TODO: change this?
        ClassTable ct = currProgram.get(Symbol.symbol(idType.s));
        if (ct == null) {
            error.complain("Class " + idType.s + " not defined ");
        } else {

            // Ensure method is defined on said class
            MethodTable mt = ct.getMethod(convertToSymbol(n.i));
            if (mt == null) {
                error.complain("Method " + n.i + " not defined for type "
                        + idType.toString());
            } else {
                returnType = mt.getReturnType();

                // Ensure parameters have the correct type
                for (int i = 0; i < n.el.size(); i++) {
                    Type et = n.el.elementAt(i).accept(this);
                    Type pt = mt.getParam(i);

                    if (!et.equals(pt)) {
                        error.complain("Parameter " + i
                                + " doesn't match expected type");
                    }
                }
            }
        }

        return returnType;
    }

    @Override
    public Type visit(IntegerLiteral n) {
        return new IntegerType();
    }

    @Override
    public Type visit(True n) {
        return new BooleanType();
    }

    @Override
    public Type visit(False n) {
        return new BooleanType();
    }

    @Override
    public Type visit(IdentifierExp n) {
        return getTypeOfVariable(Symbol.symbol(n.s));
    }

    @Override
    public Type visit(This n) {
        if (currClass != null) {
            if(currMethod != null) {
                if(currMethod.getId().equals(Symbol.symbol("main"))) {
                    error.complain("this can't be referenced from a static context");
                    return new VoidType(); // TODO: change this?
                }
            }
            
            return new IdentifierType(currClass.getId().toString());
        } else {
            error.complain("this referenced outside of class");
            return new VoidType(); // TODO: change this?
        }
    }

    @Override
    public Type visit(NewArray n) {
        if (!(n.e.accept(this) instanceof IntegerType)) {
            error.complain("Index of NewArray must be of type integer");
        }
        return new IntArrayType();
    }

    @Override
    public Type visit(NewObject n) {
        Symbol s = convertToSymbol(n.i);
        ClassTable ct = currProgram.get(s);
        if (ct != null) {
            return new IdentifierType(s.toString());
        } else {
            error.complain("Type " + s + " is not defined");
            return new VoidType(); // TODO: change this?
        }
    }

    @Override
    public Type visit(Not n) {
        if (!(n.e.accept(this) instanceof BooleanType)) {
            error.complain("Expression of " + n.getClass().getSimpleName()
                    + " must be of type boolean");
        }
        return new BooleanType();
    }

    private void complainAboutIcorrectVariableTypes(Object declarationType,
            Class<? extends Type> expected, Type actual) {
        String errorMsg = "Variable "
                + declarationType.getClass().getSimpleName();
        errorMsg += " has expected type " + expected.getSimpleName();
        errorMsg += " but has actual type " + actual.getClass().getSimpleName();
        error.complain(errorMsg);
    }

    @Override
    public Type visit(Identifier n) {
        return getTypeOfVariable(Symbol.symbol(n.s));
    }

    private static Symbol convertToSymbol(Identifier i) {
        return Symbol.symbol(i.toString());
    }

}
