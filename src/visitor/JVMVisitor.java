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
    private static final int FALSE = 0;
    private static final int TRUE = 1;

    private ProgramTable currProgram;
    private ClassTable currClass;
    private MethodTable currMethod;

    private jvm.Factory factory;
    private VMRecord currRecord;
    private VMFrame currFrame;

    private HashMap<Identifier, VMAccess> locals;
    private HashMap<String, VMAccess> fields;
    private int currStackSizeNeeded = 0;
    private int currStackSize = 0;

    private LabelTable labels;

    public JVMVisitor(ProgramTable pt, jvm.Factory factory) {
        currProgram = pt;
        currClass = null;
        currMethod = null;

        this.factory = factory;
        currRecord = null;
        currFrame = null;

        labels = new LabelTable();
        fields = new HashMap<>();
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
        currStackSize = 0;

        locals = new HashMap<>();

        String classDecl = ".class public \'" + className + "\'";
        String inheritance = ".super java/lang/Object";

        StringBuilder pre = appendOnNewline(classDecl, inheritance,
                ".method public <init>()V", "aload_0",
                "invokespecial java/lang/Object/<init>()V", "return",
                ".end method",
                ".method public static main([Ljava/lang/String;)V");

        StringBuilder post = new StringBuilder();
        for (int i = 0; i < n.vl.size(); i++) {
            post.append(n.vl.elementAt(i).accept(this) + "\n");
        }
        post.append(n.s.accept(this));

        appendOnNewline(post, "return", ".end method");

        int nrOfLocals = n.vl.size() + 1 + 1;
        appendOnNewline(pre, ".limit stack " + currStackSizeNeeded,
                ".limit locals " + nrOfLocals, post.toString());

        locals = null;
        currFrame = null;
        currRecord = null;
        currMethod = null;
        currClass = null;
        return pre.toString();
    }

    public String visit(ClassDeclSimple n) {
        currClass = currProgram.get(convertToSymbol(n.i));
        String className = currClass.getId().toString();
        currRecord = factory.newRecord(className);

        StringBuilder sb = appendOnNewline(".class public \'" + className
                + "\'", ".super java/lang/Object");

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

        setStackSize(1);

        currRecord = null;
        currClass = null;

        return sb.toString();
    }

    public String visit(ClassDeclExtends n) {
        currClass = currProgram.get(convertToSymbol(n.i));
        String className = currClass.getId().toString();
        currRecord = factory.newRecord(className);
        String superClass = currClass.getSuperClass().toString();

        StringBuilder sb = appendOnNewline(".class public \'" + className
                + "\'", ".super " + superClass);

        for (int i = 0; i < n.vl.size(); i++) {
            appendOnNewline(sb, n.vl.elementAt(i).accept(this));
        }

        appendOnNewline(sb, ".method public <init>()V");

        appendOnNewline(sb, "aload 0", "invokespecial " + superClass
                + "/<init>()V");

        appendOnNewline(sb, "return", ".end method");

        for (int i = 0; i < n.ml.size(); i++) {
            appendOnNewline(sb, n.ml.elementAt(i).accept(this));
        }

        setStackSize(1);

        currRecord = null;
        currClass = null;

        return sb.toString();
    }

    public String visit(VarDecl n) {
        VMAccess access;

        if (currFrame == null) {
            // if we don't have a frame, it's a field
            access = currRecord.allocField(n.i.toString(), n.t);
            addFieldAccess(n.i, access);
        } else {
            access = currFrame.allocLocal(n.i.toString(), n.t);
            addLocalAccess(n.i, access);
        }

        return access.declare();
    }

    public String visit(MethodDecl n) {
        currMethod = currClass.getMethod(convertToSymbol(n.i));
        currFrame = factory.newFrame(currMethod.getId().toString(), n.fl,
                currMethod.getReturnType());
        locals = new HashMap<>();
        currStackSize = 0;

        String returnType = Hardware.signature(n.t);
        StringBuilder params = new StringBuilder();
        for (int i = 0; i < n.fl.size(); i++) {
            String signature = Hardware.signature(n.fl.elementAt(i).t);
            params.append(signature);
        }
        StringBuilder pre = appendOnNewline(".method public " + n.i.s + "("
                + params.toString() + ")" + returnType);

        // allocate one spot for this
        currFrame.allocFormal("this", new IdentifierType(""));

        StringBuilder post = new StringBuilder();
        for (int i = 0; i < n.fl.size(); i++) {
            appendOnNewline(post, n.fl.elementAt(i).accept(this));
        }

        for (int i = 0; i < n.vl.size(); i++) {
            appendOnNewline(post, n.vl.elementAt(i).accept(this));
        }

        for (int i = 0; i < n.sl.size(); i++) {
            appendOnNewline(post, n.sl.elementAt(i).accept(this));
        }

        appendOnNewline(post, n.e.accept(this));

        String returnCmd;
        if (n.t instanceof IdentifierType || n.t instanceof IntArrayType) {
            returnCmd = "areturn";
        } else {
            returnCmd = "ireturn";
        }
        appendOnNewline(post, returnCmd);

        appendOnNewline(post, "return", ".end method");

        int stackSize = currStackSizeNeeded;
        // One extra local for the this variable
        int nrOfLocals = n.vl.size() + n.fl.size() + 1;
        appendOnNewline(pre, ".limit stack " + stackSize, ".limit locals "
                + nrOfLocals, post.toString());
        locals = null;
        currFrame = null;
        currMethod = null;

        return pre.toString();
    }

    public String visit(Formal n) {
        VMAccess access = currFrame.allocFormal(n.i.toString(), n.t);
        addLocalAccess(n.i, access);
        StringBuilder sb = appendOnNewline(access.load(), access.store());
        setStackSize(1);

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

        String notEquals = labels.newLabel("if_ne");
        String end = labels.newLabel("if_end");

        StringBuilder sb = appendOnNewline(exp, "ifeq " + notEquals, s1,
                "goto " + end, notEquals + ":", s2, end + ":");

        setStackSize(1);
        currStackSize = currStackSize - 1;

        return sb.toString();
    }

    public String visit(While n) {
        String exp = n.e.accept(this);
        String s = n.s.accept(this);

        String whileLabel = labels.newLabel("while_start");
        String doneLabel = labels.newLabel("while_done");

        StringBuilder sb = appendOnNewline(whileLabel + ":", exp, "ifeq "
                + doneLabel, s, "goto " + whileLabel, doneLabel + ":");

        setStackSize(1);
        currStackSize = currStackSize - 1;

        return sb.toString();
    }

    public String visit(Print n) {
        StringBuilder sb;
        String value = n.e.accept(this);

        if (n.e.getType() instanceof BooleanType) {
            String notEquals = labels.newLabel("if_ne");
            String end = labels.newLabel("if_end");
            sb = appendOnNewline("getstatic java/lang/System/out Ljava/io/PrintStream;");
            appendOnNewline(sb, value, "ifeq " + notEquals, "ldc \"true\"",
                    "goto " + end, notEquals + ":", "ldc \"false\"", end + ":");
            appendOnNewline(sb,
                    "invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V");
        } else {
            String type = Hardware.signature(n.e.getType());
            sb = appendOnNewline(
                    "getstatic java/lang/System/out Ljava/io/PrintStream;",
                    value, "invokevirtual java/io/PrintStream/println(" + type
                            + ")V");
        }

        setStackSize(2);
        currStackSize = currStackSize - 1;

        return sb.toString();
    }

    public String visit(Assign n) {
        VMAccess access = getAccess(n.i);
        StringBuilder sb = appendOnNewline(n.e.accept(this), access.store());

        setStackSize(1);
        currStackSize = currStackSize - 1;
        return sb.toString();
    }

    public String visit(ArrayAssign n) {
        VMAccess access = getAccess(n.i);
        String index = n.e1.accept(this);
        String value = n.e2.accept(this);

        StringBuilder sb = appendOnNewline(access.load(), index, value,
                "iastore");

        setStackSize(3);
        currStackSize = currStackSize - 3 + 1;
        return sb.toString();
    }

    public String visit(And n) {
        String left = n.e1.accept(this);
        String right = n.e2.accept(this);

        String evalRight = labels.newLabel("and_right");
        String end = labels.newLabel("and_end");

        StringBuilder sb = appendOnNewline(left, "ifne " + evalRight, "ldc "
                + FALSE, "goto " + end, evalRight + ":", right, end + ":");

        setStackSize(2);
        currStackSize = currStackSize - 2 + 1;
        return sb.toString();
    }

    public String visit(LessThan n) {
        String left = n.e1.accept(this);
        String right = n.e2.accept(this);

        String lessThan = labels.newLabel("lessThan_lt");
        String end = labels.newLabel("lessThan_end");

        StringBuilder sb = appendOnNewline(left, right);
        appendOnNewline(sb, "if_icmplt " + lessThan);
        appendOnNewline(sb, "ldc " + FALSE, "goto " + end, lessThan + ":",
                "ldc " + TRUE, end + ":");

        setStackSize(2);
        currStackSize = currStackSize - 2 + 1;
        return sb.toString();
    }

    public String visit(Plus n) {
        String left = n.e1.accept(this);
        String right = n.e2.accept(this);

        StringBuilder sb = appendOnNewline(left, right, "iadd");

        setStackSize(2);
        currStackSize = currStackSize - 2 + 1;
        return sb.toString();
    }

    public String visit(Minus n) {
        String left = n.e1.accept(this);
        String right = n.e2.accept(this);

        StringBuilder sb = appendOnNewline(left, right, "isub");

        setStackSize(2);
        currStackSize = currStackSize - 2 + 1;
        return sb.toString();
    }

    public String visit(Times n) {
        String left = n.e1.accept(this);
        String right = n.e2.accept(this);

        StringBuilder sb = appendOnNewline(left, right, "imul");

        setStackSize(2);
        currStackSize = currStackSize - 2 + 1;
        return sb.toString();
    }

    public String visit(ArrayLookup n) {
        String o = n.e1.accept(this);
        String i = n.e2.accept(this);
        StringBuilder sb = appendOnNewline(o, i, "iaload");

        setStackSize(2);
        currStackSize = currStackSize - 2 + 1;

        return sb.toString();
    }

    public String visit(ArrayLength n) {
        String array = n.e.accept(this);
        StringBuilder sb = appendOnNewline(array, "arraylength");

        setStackSize(1);
        currStackSize = currStackSize - 1 + 1;

        return sb.toString();
    }

    public String visit(Call n) {
        int stackNeeded = 0;

        stackNeeded++;
        StringBuilder sb = appendOnNewline(n.e.accept(this));

        StringBuilder paramTypes = new StringBuilder();
        for (int i = 0; i < n.el.size(); i++) {
            Exp exp = n.el.elementAt(i);
            stackNeeded++;
            appendOnNewline(sb, exp.accept(this));

            String type = Hardware.signature(exp.getType());
            paramTypes.append(type);
        }

        IdentifierType t = (IdentifierType) n.e.getType();
        String className = t.s;
        String returnType = Hardware.signature(n.getType());

        String methodCall = "invokevirtual " + className + "/" + n.i.s + "("
                + paramTypes.toString() + ")" + returnType;
        appendOnNewline(sb, methodCall);

        setStackSize(stackNeeded);
        currStackSize = currStackSize - stackNeeded + 1;

        return sb.toString();
    }

    public String visit(IntegerLiteral n) {
        currStackSize++;
        return "ldc " + n.i;
    }

    public String visit(True n) {
        currStackSize++;
        return "ldc " + TRUE;
    }

    public String visit(False n) {
        currStackSize++;
        return "ldc " + FALSE;
    }

    public String visit(IdentifierExp n) {
        VMAccess access = getAccess(new Identifier(n.s));
        currStackSize++;
        return access.load();
    }

    public String visit(This n) {
        currStackSize++;
        return "aload_0";
    }

    public String visit(NewArray n) {
        String size = n.e.accept(this);
        StringBuilder sb = appendOnNewline(size, "newarray int");
        setStackSize(1);
        currStackSize = currStackSize - 1 + 1;

        return sb.toString();
    }

    public String visit(NewObject n) {
        String type = n.i.s;
        StringBuilder sb = appendOnNewline("new \'" + type + "\'", "dup",
                "invokespecial " + type + "/<init>()V");

        setStackSize(2);
        currStackSize++;
        return sb.toString();
    }

    public String visit(Not n) {
        String exp = n.e.accept(this);
        String trueLbl = labels.newLabel("not_true");
        String endLbl = labels.newLabel("not_end");

        StringBuilder sb = appendOnNewline(exp, "ifeq " + trueLbl, "ldc "
                + FALSE, "goto " + endLbl, trueLbl + ":", "ldc " + TRUE, endLbl
                + ":");

        setStackSize(1);
        currStackSize = currStackSize - 1 + 1;
        return sb.toString();
    }

    public String visit(Identifier n) {
        VMAccess access = getAccess(n);
        currStackSize++;
        return access.load();
    }

    private static Symbol convertToSymbol(Identifier i) {
        return Symbol.symbol(i.toString());
    }

    private void addFieldAccess(Identifier i, VMAccess a) {
        String key = currClass.getId().toString() + i.s;
        fields.put(key, a);
    }

    private void addLocalAccess(Identifier i, VMAccess a) {
        locals.put(i, a);
    }

    private VMAccess getAccess(Identifier i) {
        VMAccess a = locals.get(i);

        if (a == null) {
            Symbol klass = currClass.getId();
            while (a == null && klass != null) {
                String key = klass.toString() + i.s;
                a = fields.get(key);
                klass = currProgram.get(klass).getSuperClass();
            }
        }

        return a;
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

    private void setStackSize(int stackNeeded) {
        currStackSizeNeeded = Math.max(stackNeeded + currStackSize,
                currStackSizeNeeded);
    }
}
