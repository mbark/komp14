package syntaxtree;

import tree.AbstractExp;
import visitor.JVMVisitor;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class IdentifierExp extends Exp {
    public String s;

    private Type t;

    public IdentifierExp(String as) {
        s = as;
    }

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
                    "IdentifierExp#getType called before typecheck!");
        }
    }
}
