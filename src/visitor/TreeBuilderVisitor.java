package visitor;

import java.util.ArrayList;
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
import tree.EXP;
import tree.ExpList;
import tree.JUMP;
import tree.LABEL;
import tree.MEM;
import tree.MOVE;
import tree.NAME;
import tree.SEQ;
import tree.Stm;
import tree.TEMP;
import frame.Access;
import frame.Factory;
import frame.Frame;

public class TreeBuilderVisitor implements TreeVisitor {
    private static final CONST TRUE = new CONST(1);
    private static final CONST FALSE = new CONST(0);

    private ProgramTable currProgram;
    private ClassTable currClass;
    private MethodTable currMethod;

    private HashMap<AbstractExp, ClassTable> classesForExp;
    private HashMap<String, AbstractExp> expForId;

    private Factory frameFactory;
    private Frame currFrame;

    public TreeBuilderVisitor(ProgramTable programTable, Factory frameFactory) {
        currProgram = programTable;
        currClass = null;
        currMethod = null;
        currFrame = null;
        this.frameFactory = frameFactory;
        classesForExp = new HashMap<>();
        expForId = new HashMap<>();
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
        currFrame = frameFactory.newFrame(
                new Label(getMethodName(currClass, new Identifier("main"))),
                new ArrayList<Boolean>(0));

        visit(n.vl);
        Stm stm = n.s.accept(this);

        currFrame = null;
        currMethod = null;
        currClass = null;
        return stm;
    }

    @Override
    public Stm visit(ClassDeclSimple n) {
        currClass = currProgram.get(convertToSymbol(n.i));
        currFrame = frameFactory.newFrame(new Label(n.i.s),
                new ArrayList<Boolean>(0));

        visit(n.vl);
        Stm methodDeclarations = visit(n.ml);

        currFrame = null;
        currClass = null;
        return methodDeclarations;
    }

    @Override
    public Stm visit(ClassDeclExtends n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AbstractExp visit(VarDecl n) {
        if (currFrame == null) {
            return new TEMP(new Temp());
        }
        Access a = currFrame.allocLocal(false);
        AbstractExp ae = a.exp(new TEMP(currFrame.FP()));
        expForId.put(n.i.s, ae);
        return ae;
    }

    @Override
    public Stm visit(MethodDecl n) {
        currMethod = currClass.getMethod(convertToSymbol(n.i));
        ArrayList<Boolean> frameFormals = new ArrayList<Boolean>(n.fl.size());
        currFrame = frameFactory.newFrame(
                new Label(getMethodName(currClass, n.i)), frameFormals);

        visit(n.fl);
        visit(n.vl);
        Stm statements = visit(n.sl);
        AbstractExp returnExp = n.e.accept(this);

        currMethod = null;
        return new SEQ(statements,
                new MOVE(new TEMP(currFrame.RV()), returnExp));
    }

    @Override
    public AbstractExp visit(Formal n) {
        Access a = currFrame.allocLocal(false);
        AbstractExp ae = a.exp(new TEMP(currFrame.FP()));

        // TODO: fix the use of identifier (for example when identifier is a
        // booleantype etc)
        classesForExp.put(ae, currProgram.get(convertToSymbol(n.i)));

        return ae;
    }

    @Override
    public Stm visit(Block n) {
        if (n.sl.size() > 0) {
            return visit(n.sl);
        } else {
            return nop();
        }
    }

    @Override
    public Stm visit(If n) {
        AbstractExp exp = n.e.accept(this);

        Stm stmt1 = n.s1.accept(this);
        Stm stmt2 = n.s2.accept(this);

        Label l1 = new Label();
        Label l2 = new Label();

        Stm cjump = new CJUMP(CJUMP.EQ, exp, TRUE, l1, l2);
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

        CJUMP checkIfDone = new CJUMP(CJUMP.EQ, exp, FALSE, done, body);
        Stm bodyStmt = n.s.accept(this);
        JUMP goToTest = new JUMP(test);

        Stm seq = toSEQ(new LABEL(test), checkIfDone, new LABEL(body),
                bodyStmt, goToTest, new LABEL(done));
        return seq;
    }

    @Override
    public Stm visit(Print n) {
        AbstractExp ae = n.e.accept(this);
        return new EXP(currFrame.externalCall("print", new ExpList(ae)));
    }

    @Override
    public Stm visit(Assign n) {
        AbstractExp value = n.e.accept(this);
        AbstractExp var = expForId.get(n.i.s);

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
        AbstractExp arrayBase = null;

        // FIXME: this is a hack!
        if (n.e1 instanceof IdentifierExp) {
            IdentifierExp ie = (IdentifierExp) n.e1;
            arrayBase = expForId.get(ie.s);
        }

        AbstractExp arrayOffset = n.e2.accept(this);
        return new MEM(new BINOP(BINOP.PLUS, arrayBase, arrayOffset));
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
        return TRUE;
    }

    @Override
    public AbstractExp visit(False n) {
        return FALSE;
    }

    @Override
    public AbstractExp visit(This n) {
        // TODO: some way to identify what this TEMP corresponds to
        return new TEMP(new Temp());
    }

    @Override
    public AbstractExp visit(NewArray n) {
        AbstractExp ae = n.e.accept(this);
        return currFrame.externalCall("initArray",
                new ExpList(ae, new CONST(0)));
    }

    @Override
    public AbstractExp visit(NewObject n) {
        // TODO: some way to identify what this TEMP corresponds to
        return new TEMP(new Temp());
    }

    @Override
    public AbstractExp visit(Not n) {
        AbstractExp e = n.e.accept(this);

        // FIXME: use `=(e, FALSE)`?
        return new BINOP(BINOP.AND, e, FALSE);
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

    private void visit(VarDeclList vdl) {
        for (int i = 0; i < vdl.size(); i++) {
            vdl.elementAt(i).accept(this);
        }
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

    private Stm nop() {
        return new EXP(new CONST(0));
    }

    @Override
    public AbstractExp visit(IdentifierExp n) {
        return expForId.get(n.s);
    }

    @Override
    public AbstractExp visit(Identifier n) {
        return null;
    }

    @Override
    public AbstractExp visit(IntArrayType n) {
        return null;
    }

    @Override
    public AbstractExp visit(BooleanType n) {
        return null;
    }

    @Override
    public AbstractExp visit(IntegerType n) {
        return null;
    }

    @Override
    public AbstractExp visit(VoidType n) {
        return null;
    }

    @Override
    public AbstractExp visit(IdentifierType n) {
        return null;
    }
}