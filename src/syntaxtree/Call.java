package syntaxtree;

import tree.AbstractExp;
import visitor.JVMVisitor;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class Call extends Exp {
    public Exp e;
    public Identifier i;
    public ExpList el;

    private Type t;

    public Call(Exp ae, Identifier ai, ExpList ael) {
        e = ae;
        i = ai;
        el = ael;
        t = null;
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
                    "Call#getType called before typecheck!");
        }
    }
}
