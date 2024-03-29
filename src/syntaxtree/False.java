package syntaxtree;

import tree.AbstractExp;
import visitor.JVMVisitor;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class False extends Exp {
    public void accept(Visitor v) {
        v.visit(this);
    }

    public String accept(JVMVisitor v) {
        return v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public AbstractExp accept(TreeVisitor v) {
        return v.visit(this);
    }

    @Override
    public Type getType() {
        return new BooleanType();
    }
}
