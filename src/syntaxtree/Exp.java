package syntaxtree;

import tree.AbstractExp;
import visitor.JVMVisitor;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public abstract class Exp {
    public abstract void accept(Visitor v);
    
    public abstract String accept(JVMVisitor v);

    public abstract Type accept(TypeVisitor v);

    public abstract AbstractExp accept(TreeVisitor v);
}
