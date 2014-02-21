package syntaxtree;

import visitor.TypeVisitor;
import visitor.Visitor;

public class Formal {
    public Type t;
    public Identifier i;

    public Formal(Type at, Identifier ai) {
        t = at;
        i = ai;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }
}
