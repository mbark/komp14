package syntaxtree;

import tree.AbstractExp;
import visitor.JVMVisitor;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class This extends Exp {
    private Type t;

    public void accept(Visitor v) {
        v.visit(this);
    }

    public String accept(JVMVisitor v) {
        return v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        t = v.visit(this);
        return t;
    }

    public AbstractExp accept(TreeVisitor v) {
        return v.visit(this);
    }

    @Override
    public Type getType() {
        if (t != null) {
            return t;
        } else {
            throw new IllegalStateException(
                    "This#getType called before typecheck!");
        }
    }
}
