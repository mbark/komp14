package syntaxtree;

import tree.Stm;
import visitor.JVMVisitor;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class MethodDecl {
    public Type t;
    public Identifier i;
    public FormalList fl;
    public VarDeclList vl;
    public StatementList sl;
    public Exp e;

    public MethodDecl(Type at, Identifier ai, FormalList afl, VarDeclList avl,
            StatementList asl, Exp ae) {
        t = at;
        i = ai;
        fl = afl;
        vl = avl;
        sl = asl;
        e = ae;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public String accept(JVMVisitor v) {
        return v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public Stm accept(TreeVisitor v) {
        return v.visit(this);
    }
}
