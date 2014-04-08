package syntaxtree;

import tree.Stm;
import visitor.JVMVisitor;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public abstract class ClassDecl {
    public abstract void accept(Visitor v);

    public abstract Type accept(TypeVisitor v);

    public abstract Stm accept(TreeVisitor v);

    public abstract String accept(JVMVisitor jvmVisitor);

    public abstract String getName();
}
