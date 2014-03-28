package syntaxtree;

import tree.Stm;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class This extends Exp {
    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public Stm accept(TreeVisitor v) {
        return v.visit(this);
    }
}
