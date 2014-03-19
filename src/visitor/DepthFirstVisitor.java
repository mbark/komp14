package visitor;

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
import syntaxtree.ExpList;
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
import syntaxtree.Statement;
import syntaxtree.StatementList;
import syntaxtree.This;
import syntaxtree.Times;
import syntaxtree.True;
import syntaxtree.Type;
import syntaxtree.VarDecl;
import syntaxtree.VarDeclList;
import syntaxtree.VoidType;
import syntaxtree.While;

public abstract class DepthFirstVisitor implements Visitor {

    @Override
    public void visit(Program n) {
        visit(n.m);
        for (int i = 0; i < n.cl.size(); i++) {
            ClassDecl cd = n.cl.elementAt(i);
            visit(cd);
        }
    }

    @Override
    public void visit(MainClass n) {
        visit(n.i1);
        visit(n.i2);
        visit(n.vl);
        visit(n.s);
    }

    @Override
    public void visit(ClassDeclSimple n) {
        visit(n.i);
        visit(n.vl);
        visit(n.ml);
    }

    @Override
    public void visit(ClassDeclExtends n) {
        throw new UnsupportedOperationException(n.getClass().getName()
                + " not implemented yet!");
    }

    @Override
    public void visit(VarDecl n) {
        visit(n.t);
        visit(n.i);
    }

    @Override
    public void visit(MethodDecl n) {
        visit(n.t);
        visit(n.i);
        visit(n.fl);
        visit(n.vl);
        visit(n.sl);
        visit(n.e);
    }

    @Override
    public void visit(Formal n) {
        visit(n.t);
        visit(n.i);
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
    public void visit(VoidType n) {
        // No-op
    }

    @Override
    public void visit(IdentifierType n) {
        // No-op
    }

    @Override
    public void visit(Block n) {
        visit(n.sl);
    }

    @Override
    public void visit(If n) {
        visit(n.e);
        visit(n.s1);
        visit(n.s2);
    }

    @Override
    public void visit(While n) {
        visit(n.e);
        visit(n.s);
    }

    @Override
    public void visit(Print n) {
        visit(n.e);
    }

    @Override
    public void visit(Assign n) {
        visit(n.i);
        visit(n.e);
    }

    @Override
    public void visit(ArrayAssign n) {
        visit(n.i);
        visit(n.e1);
        visit(n.e2);
    }

    @Override
    public void visit(And n) {
        visit(n.e1);
        visit(n.e2);
    }

    @Override
    public void visit(LessThan n) {
        visit(n.e1);
        visit(n.e2);
    }

    @Override
    public void visit(Plus n) {
        visit(n.e1);
        visit(n.e2);
    }

    @Override
    public void visit(Minus n) {
        visit(n.e1);
        visit(n.e2);
    }

    @Override
    public void visit(Times n) {
        visit(n.e1);
        visit(n.e2);
    }

    @Override
    public void visit(ArrayLookup n) {
        visit(n.e1);
        visit(n.e2);
    }

    @Override
    public void visit(ArrayLength n) {
        visit(n.e);
    }

    @Override
    public void visit(Call n) {
        visit(n.e);
        visit(n.i);
        visit(n.el);
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
        visit(n.e);
    }

    @Override
    public void visit(NewObject n) {
        visit(n.i);
    }

    @Override
    public void visit(Not n) {
        visit(n.e);
    }

    @Override
    public void visit(Identifier n) {
        // No-op
    }

    private void visit(Exp e) {
        if (e instanceof IntegerLiteral) {
            visit((IntegerLiteral) e);
        } else if (e instanceof Plus) {
            visit((Plus) e);
        } else if (e instanceof ArrayLookup) {
            visit((ArrayLookup) e);
        } else if (e instanceof Call) {
            visit((Call) e);
        } else if (e instanceof IdentifierExp) {
            visit((IdentifierExp) e);
        } else if (e instanceof NewObject) {
            visit((NewObject) e);
        } else if (e instanceof NewArray) {
            visit((NewArray) e);
        } else if (e instanceof LessThan) {
            visit((LessThan) e);
        } else if (e instanceof Not) {
            visit((Not) e);
        } else if (e instanceof True) {
            visit((True) e);
        } else if (e instanceof False) {
            visit((False) e);
        } else if (e instanceof And) {
            visit((And) e);
        } else if (e instanceof ArrayLength) {
            visit((ArrayLength) e);
        } else if (e instanceof Times) {
            visit((Times) e);
        } else if (e instanceof This) {
            visit((This) e);
        } else {
            throw new UnsupportedOperationException("visit(Exp: "
                    + e.getClass().getName() + ") not implemented yet!");
        }
    }

    private void visit(Statement s) {
        if (s instanceof Print) {
            visit((Print) s);
        } else if (s instanceof Block) {
            visit((Block) s);
        } else if (s instanceof Assign) {
            visit((Assign) s);
        } else if (s instanceof While) {
            visit((While) s);
        } else if (s instanceof If) {
            visit((If) s);
        } else if (s instanceof ArrayAssign) {
            visit((ArrayAssign) s);
        } else {
            throw new UnsupportedOperationException("visit(Statement: "
                    + s.getClass().getName() + ") not implemented yet!");
        }
    }

    private void visit(ClassDecl cd) {
        if (cd instanceof ClassDeclSimple) {
            visit((ClassDeclSimple) cd);
        } else if (cd instanceof ClassDeclExtends) {
            visit((ClassDeclExtends) cd);
        } else {
            throw new UnsupportedOperationException("visit(ClassDecl: "
                    + cd.getClass().getName() + ") not implemented yet!");
        }
    }

    private void visit(MethodDeclList ml) {
        for (int i = 0; i < ml.size(); i++) {
            MethodDecl md = ml.elementAt(i);
            visit(md);
        }
    }

    private void visit(VarDeclList vl) {
        for (int i = 0; i < vl.size(); i++) {
            VarDecl vd = vl.elementAt(i);
            visit(vd);
        }
    }

    private void visit(ExpList el) {
        for (int i = 0; i < el.size(); i++) {
            Exp e = el.elementAt(i);
            visit(e);
        }
    }

    private void visit(Type t) {
        if (t instanceof IntArrayType) {
            visit((IntArrayType) t);
        } else if (t instanceof BooleanType) {
            visit((BooleanType) t);
        } else if (t instanceof IntegerType) {
            visit((IntegerType) t);
        } else if (t instanceof IdentifierType) {
            visit((IdentifierType) t);
        } else {
            throw new UnsupportedOperationException("visit(Type: "
                    + t.getClass().getName() + ") not implemented yet!");
        }
    }

    private void visit(StatementList sl) {
        for (int i = 0; i < sl.size(); i++) {
            Statement s = sl.elementAt(i);
            visit(s);
        }
    }

    private void visit(FormalList fl) {
        for (int i = 0; i < fl.size(); i++) {
            Formal f = fl.elementAt(i);
            visit(f);
        }
    }

}
