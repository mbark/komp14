package syntaxtree;

import tree.Stm;
import visitor.JVMVisitor;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public abstract class Statement {
    public abstract void accept(Visitor v);

    public abstract String accept(JVMVisitor v);

    public abstract Type accept(TypeVisitor v);

    public abstract Stm accept(TreeVisitor v);
}
