package visitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;

import jvm.Hardware;
import jvm.LabelTable;
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
import syntaxtree.ClassDecl;
import syntaxtree.ClassDeclExtends;
import syntaxtree.ClassDeclSimple;
import syntaxtree.Exp;
import syntaxtree.False;
import syntaxtree.Formal;
import syntaxtree.FormalList;
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
    private LabelTable labels;

    private static final int FALSE = 0;
    private static final int TRUE = 1;

    public JVMVisitor(ProgramTable pt, jvm.Factory factory) {
        currProgram = pt;
        currClass = null;
        currMethod = null;

        this.factory = factory;
        currRecord = null;
        currFrame = null;

        accesses = new HashMap<>();
        labels = new LabelTable();
    }

    public String visit(Program n) {
        MainClass mc = n.m;
        writeClassToFile(mc.i1.s, mc.accept(this));

        for (int i = 0; i < n.cl.size(); i++) {
            ClassDecl cd = n.cl.elementAt(i);
            writeClassToFile(cd.getName(), cd.accept(this));
        }

        return "";
    }

    private void writeClassToFile(String className, String assembly) {
        File assemblyFile = new File("./" + className + ".s");
        try {
            PrintWriter pw = new PrintWriter(assemblyFile);
            pw.write(assembly);
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace(System.err);
            System.exit(1);
        }
    }

    public String visit(MainClass n) {
        currClass = currProgram.get(convertToSymbol(n.i1));
        String className = currClass.getId().toString();
        currRecord = factory.newRecord(className);

        currMethod = currClass.getMethod(Symbol.symbol("main"));
        currFrame = factory.newFrame("main", new FormalList(),
                currMethod.getReturnType());

        String classDecl = ".class public \'" + className + "\'";
        String inheritance = ".super java/lang/Object";

        StringBuilder sb = appendOnNewline(classDecl, inheritance,
                ".method public <init>()V", "aload_0",
                "invokespecial java/lang/Object/<init>()V", "return",
                ".end method",
                ".method public static main([Ljava/lang/String;)V");

        // TODO: hard coded values
        int stackSize = 50;
        int locals = 50;
        appendOnNewline(sb, ".limit stack " + stackSize, ".limit locals "
                + locals);

        for (int i = 0; i < n.vl.size(); i++) {
            sb.append(n.vl.elementAt(i).accept(this) + "\n");
        }
        sb.append(n.s.accept(this));

        appendOnNewline(sb, "return", ".end method");

        currFrame = null;
        currRecord = null;
        currClass = null;
        return sb.toString();
    }

    public String visit(ClassDeclSimple n) {
        currClass = currProgram.get(convertToSymbol(n.i));
        String className = currClass.getId().toString();
        currRecord = factory.newRecord(className);

        StringBuilder sb = appendOnNewline(".class public \'" + className + "\'",
                ".super java/lang/Object");

        for (int i = 0; i < n.vl.size(); i++) {
            appendOnNewline(sb, n.vl.elementAt(i).accept(this));
        }

        appendOnNewline(sb, ".method public <init>()V");

        appendOnNewline(sb, "aload 0",
                "invokespecial java/lang/Object/<init>()V");

        appendOnNewline(sb, "return", ".end method");

        for (int i = 0; i < n.ml.size(); i++) {
            appendOnNewline(sb, n.ml.elementAt(i).accept(this));
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
        return access.declare();
    }

    public String visit(MethodDecl n) {
        currMethod = currClass.getMethod(convertToSymbol(n.i));
        currFrame = factory.newFrame(currMethod.getId().toString(), n.fl,
                currMethod.getReturnType());

        String returnType = Hardware.signature(n.t);
        StringBuilder params = new StringBuilder();
        for(int i = 0; i<n.fl.size(); i++) {
            String signature = Hardware.signature(n.fl.elementAt(i).t);
            params.append(signature);
        }
        StringBuilder sb = appendOnNewline(".method public " + n.i.s + "(" + params.toString() + ")"
                + returnType);
        // TODO: hard coded values
        int stackSize = 50;
        int locals = 50;
        appendOnNewline(sb, ".limit stack " + stackSize, ".limit locals "
                + locals);

//        allocate one spot for this
        currFrame.allocFormal("this", new IdentifierType(""));
        
        for (int i = 0; i < n.fl.size(); i++) {
            appendOnNewline(sb, n.fl.elementAt(i).accept(this));
        }

        for (int i = 0; i < n.vl.size(); i++) {
            appendOnNewline(sb, n.vl.elementAt(i).accept(this));
        }

        for (int i = 0; i < n.sl.size(); i++) {
            appendOnNewline(sb, n.sl.elementAt(i).accept(this));
        }

        appendOnNewline(sb, n.e.accept(this));

        String returnCmd;
        if (n.t instanceof IntegerType) {
            returnCmd = "ireturn";
        } else {
            returnCmd = "areturn";
        }
        appendOnNewline(sb, returnCmd);

        appendOnNewline(sb, "return", ".end method");

        currFrame = null;
        currMethod = null;

        return sb.toString();
    }

    public String visit(Formal n) {
        VMAccess access = currFrame.allocFormal(n.i.toString(), n.t);
        addAccess(n.i, access);
        StringBuilder sb = appendOnNewline(access.load(), access.store());

        return sb.toString();
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
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n.sl.size(); i++) {
            appendOnNewline(sb, n.sl.elementAt(i).accept(this));
        }

        return sb.toString();
    }

    public String visit(If n) {
        String exp = n.e.accept(this);
        String s1 = n.s1.accept(this);
        String s2 = n.s2.accept(this);

        String notEquals = labels.newLabel("ne");
        String end = labels.newLabel("end");

        StringBuilder sb = appendOnNewline(exp, "ifeq " + notEquals, s1,
                "goto " + end, notEquals + ":", s2, end + ":");

        return sb.toString();
    }

    public String visit(While n) {
        String exp = n.e.accept(this);
        String s = n.s.accept(this);

        String whileLabel = labels.newLabel("while");
        String doneLabel = labels.newLabel("done");

        StringBuilder sb = appendOnNewline(whileLabel + ":", exp, "ifeq "
                + doneLabel, s, "goto " + whileLabel, doneLabel + ":");

        return sb.toString();
    }

    public String visit(Print n) {
        String type = Hardware.signature(n.e.getType());
        StringBuilder sb = appendOnNewline(
                "getstatic java/lang/System/out Ljava/io/PrintStream;",
                n.e.accept(this), "invokevirtual java/io/PrintStream/println("
                        + type + ")V");

        return sb.toString();
    }

    public String visit(Assign n) {
        VMAccess access = getAccess(n.i);
        StringBuilder sb = appendOnNewline(n.e.accept(this), access.store());
        return sb.toString();
    }

    public String visit(ArrayAssign n) {
        VMAccess access = getAccess(n.i);
        String index = n.e1.accept(this);
        String value = n.e2.accept(this);

        StringBuilder sb = appendOnNewline(access.load(), index, value,
                "iastore");

        return sb.toString();
    }

    public String visit(And n) {
        String left = n.e1.accept(this);
        String right = n.e2.accept(this);

        String trueLabel = labels.newLabel("true");
        String end = labels.newLabel("end");

        StringBuilder sb = appendOnNewline(left, "dup", "ifne " + trueLabel,
                "ldc " + FALSE, "goto " + end, trueLabel + ":", right, end
                        + ":");
        appendOnNewline(sb, "iand");
        return sb.toString();
    }

    public String visit(LessThan n) {
        String left = n.e1.accept(this);
        String right = n.e2.accept(this);

        String greaterThan = labels.newLabel("gt");
        String end = labels.newLabel("end");

        StringBuilder sb = appendOnNewline(right, left);
        appendOnNewline(sb, "if_icmple " + greaterThan);
        appendOnNewline(sb, "iconst_1", "goto " + end, greaterThan + ":",
                "iconst_0", end + ":");

        return sb.toString();
    }

    public String visit(Plus n) {
        String left = n.e1.accept(this);
        String right = n.e2.accept(this);

        StringBuilder sb = appendOnNewline(left, right, "iadd");
        return sb.toString();
    }

    public String visit(Minus n) {
        String left = n.e1.accept(this);
        String right = n.e2.accept(this);

        StringBuilder sb = appendOnNewline(left, right, "isub");
        return sb.toString();
    }

    public String visit(Times n) {
        String left = n.e1.accept(this);
        String right = n.e2.accept(this);

        StringBuilder sb = appendOnNewline(left, right, "imul");
        return sb.toString();
    }

    public String visit(ArrayLookup n) {
        String o = n.e1.accept(this);
        String i = n.e2.accept(this);
        StringBuilder sb = appendOnNewline(o, i, "iaload");

        return sb.toString();
    }

    public String visit(ArrayLength n) {
        String array = n.e.accept(this);
        StringBuilder sb = appendOnNewline(array, "arraylength");
        return sb.toString();
    }

    public String visit(Call n) {
        StringBuilder sb = appendOnNewline(n.e.accept(this));

        StringBuilder paramTypes = new StringBuilder();
        for (int i = 0; i < n.el.size(); i++) {
            Exp exp = n.el.elementAt(i);
            appendOnNewline(sb, exp.accept(this));

            String type = Hardware.signature(exp.getType());
            // TODO: is there some separator between them?
            paramTypes.append(type);
        }

        IdentifierType t = (IdentifierType) n.e.getType();
        String className = t.s;
        String returnType = Hardware.signature(n.getType());

        String methodCall = "invokevirtual " + className + "/" + n.i.s + "("
                + paramTypes.toString() + ")" + returnType;
        appendOnNewline(sb, methodCall);

        return sb.toString();
    }

    public String visit(IntegerLiteral n) {
        return "ldc " + n.i;
    }

    public String visit(True n) {
        return "ldc " + TRUE;
    }

    public String visit(False n) {
        return "ldc " + FALSE;
    }

    public String visit(IdentifierExp n) {
        VMAccess access = getAccess(new Identifier(n.s));
        return access.load();
    }

    public String visit(This n) {
        // TODO: I think this is how you do it
        return "aload_0";
    }

    public String visit(NewArray n) {
        String size = n.e.accept(this);
        StringBuilder sb = appendOnNewline(size, "newarray int");

        return sb.toString();
    }

    public String visit(NewObject n) {
        String type = n.i.s;
        StringBuilder sb = appendOnNewline("new \'" + type + "\'", "dup",
                "invokespecial " + type + "/<init>()V");
        return sb.toString();
    }

    public String visit(Not n) {
        String exp = n.e.accept(this);
        String trueLbl = labels.newLabel("true");
        String endLbl = labels.newLabel("end");

        // if true, push false else push true
        StringBuilder sb = appendOnNewline(exp, "ifeq " + trueLbl, "ldc "
                + FALSE, "goto " + endLbl,  trueLbl + ":", "ldc " + TRUE, endLbl + ":");

        return sb.toString();
    }

    public String visit(Identifier n) {
        VMAccess access = getAccess(n);
        return access.load();
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

    private StringBuilder appendOnNewline(String... s) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < s.length; i++) {
            sb.append(s[i] + "\n");
        }

        return sb;
    }

    private void appendOnNewline(StringBuilder sb, String... s) {
        for (int i = 0; i < s.length; i++) {
            sb.append(s[i] + "\n");
        }
    }
}
