package syntaxtree;

import tree.Stm;
import visitor.JVMVisitor;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class ClassDeclSimple extends ClassDecl {
    public Identifier i;
    public VarDeclList vl;
    public MethodDeclList ml;

    public ClassDeclSimple(Identifier ai, VarDeclList avl, MethodDeclList aml) {
        i = ai;
        vl = avl;
        ml = aml;
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
