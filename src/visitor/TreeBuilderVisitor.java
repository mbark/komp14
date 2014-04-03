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
import syntaxtree.MethodDeclList;
import syntaxtree.Minus;
import syntaxtree.NewArray;
import syntaxtree.NewObject;
import syntaxtree.Not;
import syntaxtree.Plus;
import syntaxtree.Print;
import syntaxtree.Program;
import syntaxtree.StatementList;
import syntaxtree.This;
import syntaxtree.Times;
import syntaxtree.True;
import syntaxtree.VarDecl;
import syntaxtree.VarDeclList;
import syntaxtree.VoidType;
import syntaxtree.While;
import temp.Label;
import temp.Temp;
import tree.AbstractExp;
import tree.BINOP;
import tree.CALL;
import tree.CJUMP;
import tree.CONST;
import tree.ExpList;
import tree.JUMP;
import tree.LABEL;
import tree.MEM;
import tree.MOVE;
import tree.NAME;
import tree.SEQ;
import tree.Stm;
import tree.TEMP;
import frame.Frame;

public class TreeBuilderVisitor implements TreeVisitor {

    private ProgramTable currProgram;
    private ClassTable currClass;
    private MethodTable currMethod;

    private HashMap<AbstractExp, ClassTable> classesForExp;

    // TODO: use this
    private Frame frame;

    public TreeBuilderVisitor(ProgramTable programTable) {
        currProgram = programTable;
        currClass = null;
        currMethod = null;

        classesForExp = new HashMap<>();

        frame = null;
    }

    @Override
    public Stm visit(Program n) {
        Stm stm = n.m.accept(this);

        for (int i = 0; i < n.cl.size(); i++) {
            stm = new SEQ(stm, n.cl.elementAt(i).accept(this));
        }

        return stm;
    }

    @Override
    public Stm visit(MainClass n) {
        currClass = currProgram.get(convertToSymbol(n.i1));
        currMethod = currClass.getMethod(Symbol.symbol("main"));

        visit(n.vl);
        Stm stm = n.s.accept(this);

        currMethod = null;
        currClass = null;
        return stm;
    }

    @Override
    public Stm visit(ClassDeclSimple n) {
        currClass = currProgram.get(convertToSymbol(n.i));

        // TODO: implement the vardecl visit
        ExpList varDecls = visit(n.vl);
        Stm methodDeclarations = visit(n.ml);

        currClass = null;
        return methodDeclarations;
    }

    @Override
    public Stm visit(ClassDeclExtends n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AbstractExp visit(VarDecl n) {
        // TODO: proper offset
        int offset = 0;
        Temp fp = frame == null ? new Temp() : frame.FP();

        AbstractExp exp = new MEM(new BINOP(BINOP.PLUS, new TEMP(fp),
                new CONST(offset)));
        addExp(exp, n.i);

        return exp;
    }

    @Override
    public Stm visit(MethodDecl n) {
        currMethod = currClass.getMethod(convertToSymbol(n.i));
        // TODO Auto-generated method stub

        ExpList formals = visit(n.fl);
        ExpList varDecls = visit(n.vl);
        Stm statements = visit(n.sl);

        AbstractExp returnExp = n.e.accept(this);

        // TODO: how to combine these into one Stm?

        currMethod = null;
        return null;
    }

    @Override
    public AbstractExp visit(Formal n) {
        // TODO: proper offset
        int offset = 0;
        Temp fp = frame == null ? new Temp() : frame.FP();

        AbstractExp exp = new MEM(new BINOP(BINOP.PLUS, new TEMP(fp),
                new CONST(offset)));
        addExp(exp, n.i);

        return exp;
    }

    private void addExp(AbstractExp exp, Identifier i) {
        // TODO: fix the use of identifier (for example when identifier is a
        // booleantype etc)
        classesForExp.put(exp, currProgram.get(convertToSymbol(i)));
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
        return visit(n.sl);
    }

    @Override
    public Stm visit(If n) {
        AbstractExp exp = n.e.accept(this);

        Stm stmt1 = n.s1.accept(this);
        Stm stmt2 = n.s2.accept(this);

        Label l1 = new Label();
        Label l2 = new Label();

        Stm cjump = new CJUMP(CJUMP.EQ, exp, new CONST(1), l1, l2);
        Stm jump1 = toSEQ(new LABEL(l1), stmt1);
        Stm jump2 = toSEQ(new LABEL(l2), stmt2);

        return toSEQ(cjump, jump1, jump2);
    }

    @Override
    public Stm visit(While n) {
        AbstractExp exp = n.e.accept(this);

        Label done = new Label();
        Label body = new Label();
        Label test = new Label();

        CJUMP checkIfDone = new CJUMP(CJUMP.NE, exp, new CONST(0), done, body);
        Stm bodyStmt = n.s.accept(this);
        JUMP goToTest = new JUMP(test);

        Stm seq = toSEQ(new LABEL(test), checkIfDone, new LABEL(body),
                bodyStmt, goToTest, new LABEL(done));
        return seq;
    }

    @Override
    public Stm visit(Print n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Stm visit(Assign n) {
        AbstractExp value = n.e.accept(this);
        AbstractExp var = n.i.accept(this);

        return new MOVE(var, value);
    }

    @Override
    public Stm visit(ArrayAssign n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(And n) {
        AbstractExp left = n.e1.accept(this);
        AbstractExp right = n.e2.accept(this);

        return new BINOP(BINOP.AND, left, right);
    }

    @Override
    public AbstractExp visit(LessThan n) {
        AbstractExp left = n.e1.accept(this);
        AbstractExp right = n.e2.accept(this);

        // TODO: implement
        return null;
    }

    @Override
    public AbstractExp visit(Plus n) {
        AbstractExp left = n.e1.accept(this);
        AbstractExp right = n.e2.accept(this);

        return new BINOP(BINOP.PLUS, left, right);
    }

    @Override
    public AbstractExp visit(Minus n) {
        AbstractExp left = n.e1.accept(this);
        AbstractExp right = n.e2.accept(this);

        return new BINOP(BINOP.MINUS, left, right);
    }

    @Override
    public AbstractExp visit(Times n) {
        AbstractExp left = n.e1.accept(this);
        AbstractExp right = n.e2.accept(this);

        return new BINOP(BINOP.MUL, left, right);
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
        ClassTable klass = classesForExp.get(callee);

        String methodName = getMethodName(klass, n.i);
        Label methodLabel = new Label(methodName);

        return new CALL(new NAME(methodLabel), visit(n.el));
    }

    @Override
    public AbstractExp visit(IntegerLiteral n) {
        return new CONST(n.i);
    }

    @Override
    public AbstractExp visit(True n) {
        return new CONST(1);
    }

    @Override
    public AbstractExp visit(False n) {
        return new CONST(0);
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
        return new TEMP(new Temp());
    }

    @Override
    public AbstractExp visit(Not n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AbstractExp visit(Identifier n) {
        return new TEMP(new Temp());
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

    private ExpList visit(FormalList fl) {
        tree.ExpList telHead = null;
        tree.ExpList telTail = null;

        for (int i = 0; i < fl.size(); i++) {
            AbstractExp ae = fl.elementAt(i).accept(this);
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

    private ExpList visit(VarDeclList vdl) {
        tree.ExpList telHead = null;
        tree.ExpList telTail = null;

        for (int i = 0; i < vdl.size(); i++) {
            AbstractExp ae = vdl.elementAt(i).accept(this);
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

    private Stm visit(StatementList sl) {
        SEQ seq = null;
        Stm tmp = null;

        for (int i = 0; i < sl.size(); i++) {
            Stm stm = sl.elementAt(i).accept(this);
            if (seq == null) {
                if (tmp == null) {
                    tmp = stm;
                } else {
                    seq = new SEQ(tmp, stm);
                }
            } else {
                seq = new SEQ(seq, stm);
            }
        }
        if (seq == null) {
            return tmp;
        } else {
            return seq;
        }
    }

    private Stm visit(MethodDeclList sl) {
        SEQ seq = null;
        Stm tmp = null;

        for (int i = 0; i < sl.size(); i++) {
            Stm stm = sl.elementAt(i).accept(this);
            if (seq == null) {
                if (tmp == null) {
                    tmp = stm;
                } else {
                    seq = new SEQ(tmp, stm);
                }
            } else {
                seq = new SEQ(seq, stm);
            }
        }
        if (seq == null) {
            return tmp;
        } else {
            return seq;
        }
    }

    private String getMethodName(ClassTable klass, Identifier method) {
        if (klass == null || klass.getId() == null) {
            return "_" + method.s;
        } else {
            return klass.getId().toString() + "_" + method.s;
        }
    }

    private Stm toSEQ(Stm... stmts) {
        SEQ seq = null;
        Stm tmp = null;

        for (int i = stmts.length - 1; i >= 0; i--) {
            if (seq == null) {
                if (tmp == null) {
                    tmp = stmts[i];
                } else {
                    seq = new SEQ(stmts[i], tmp);
                }
            } else {
                seq = new SEQ(stmts[i], seq);
            }
        }

        if (seq == null) {
            return tmp;
        } else {
            return seq;
        }
    }
}