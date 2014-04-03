package visitor;

import java.util.HashMap;

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
import frame.VMAccess;
import frame.VMFrame;
import frame.VMRecord;

public class JVMVisitor {
    private ProgramTable currProgram;
    private ClassTable currClass;
    private MethodTable currMethod;

    private jvm.Factory factory;
    private VMRecord currRecord;
    private VMFrame currFrame;

    private HashMap<Identifier, VMAccess> accesses;

    public JVMVisitor(ProgramTable pt, jvm.Factory factory) {
        currProgram = pt;
        currClass = null;
        currMethod = null;

        this.factory = factory;
        currRecord = null;
        currFrame = null;

        accesses = new HashMap<>();
    }

    public String visit(Program n) {
        StringBuilder sb = new StringBuilder();
        String s = n.m.accept(this);

        sb.append(s + "\n");
        for (int i = 0; i < n.cl.size(); i++) {
            sb.append(n.cl.elementAt(i).accept(this) + "\n");
        }

        return sb.toString();
    }

    public String visit(MainClass n) {
        currClass = currProgram.get(convertToSymbol(n.i1));
        currRecord = factory.newRecord(currClass.getId().toString());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n.vl.size(); i++) {
            sb.append(n.vl.elementAt(i).accept(this) + "\n");
        }
        sb.append(n.s.accept(this));

        currRecord = null;
        currClass = null;
        return sb.toString();
    }

    public String visit(ClassDeclSimple n) {
        // TODO Auto-generated method stub

        currClass = currProgram.get(convertToSymbol(n.i));
        currRecord = factory.newRecord(currClass.getId().toString());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n.vl.size(); i++) {
            String s = n.vl.elementAt(i).accept(this);
            sb.append(s + "\n");
        }

        currRecord = null;
        currClass = null;

        return sb.toString();
    }

    public String visit(ClassDeclExtends n) {
        throw new UnsupportedOperationException();
    }

    public String visit(VarDecl n) {
        VMAccess access;

        if (currFrame == null) {
            // if we don't have a frame, it's a field
            access = currRecord.allocField(n.i.toString(), n.t);
        } else {
            access = currFrame.allocLocal(n.i.toString(), n.t);
        }

        addAccess(n.i, access);

        return access.store();
    }

    public String visit(MethodDecl n) {
        currMethod = currClass.getMethod(convertToSymbol(n.i));
        currFrame = factory.newFrame(currMethod.getId().toString(), n.fl,
                currMethod.getReturnType());

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n.fl.size(); i++) {
            String s = n.fl.elementAt(i).accept(this);
            sb.append(s + "\n");
        }

        for (int i = 0; i < n.vl.size(); i++) {
            String s = n.vl.elementAt(i).accept(this);
            sb.append(s + "\n");
        }

        currFrame = null;
        currMethod = null;

        return sb.toString();
    }

    public String visit(Formal n) {
        VMAccess access = currFrame.allocFormal(n.i.toString(), n.t);
        addAccess(n.i, access);

        return access.store();
    }

    public String visit(IntArrayType n) {
        throw new UnsupportedOperationException();
    }

    public String visit(BooleanType n) {
        throw new UnsupportedOperationException();
    }

    public String visit(IntegerType n) {
        throw new UnsupportedOperationException();
    }

    public String visit(IdentifierType n) {
        throw new UnsupportedOperationException();
    }

    public String visit(VoidType n) {
        throw new UnsupportedOperationException();
    }

    public String visit(Block n) {
        for (int i = 0; i < n.sl.size(); i++) {
            n.sl.elementAt(i).accept(this);
        }

        return null;
    }

    public String visit(If n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(While n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(Print n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(Assign n) {
        VMAccess access = getAccess(n.i);

        String s = n.e.accept(this);
        if (s == null) {
            return "null";
        }
        s += "\n " + access.store();

        return s;
    }

    public String visit(ArrayAssign n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(And n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(LessThan n) {
        // TODO Auto-generated method stub
        String left = n.e1.accept(this);
        String right = n.e2.accept(this);

        int offset = 0;

        String s = left + "\n" + right;
        s += "\n" + "if_icmpge " + offset;

        s += "\n" + "iconst_1";
        s += "\n" + "goto " + (offset + 1);
        s += "\n" + "iconst_0";
        s += "\n" + "istore_3";

        return s;
    }

    public String visit(Plus n) {
        return "iadd";
    }

    public String visit(Minus n) {
        return "isub";
    }

    public String visit(Times n) {
        return "imul";
    }

    public String visit(ArrayLookup n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(ArrayLength n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(Call n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(IntegerLiteral n) {
        return "ldc " + n.i;
    }

    public String visit(True n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(False n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(IdentifierExp n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(This n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(NewArray n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(NewObject n) {
        // TODO: Auto-generated method stub
        return null;
    }

    public String visit(Not n) {
        // TODO Auto-generated method stub
        return null;
    }

    public String visit(Identifier n) {
        // TODO Auto-generated method stub
        return null;
    }

    private static Symbol convertToSymbol(Identifier i) {
        return Symbol.symbol(i.toString());
    }

    private void addAccess(Identifier i, VMAccess a) {
        accesses.put(i, a);
    }

    private VMAccess getAccess(Identifier i) {
        return accesses.get(i);
    }
}
