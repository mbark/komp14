package syntaxtree;

import visitor.TypeVisitor;
import visitor.Visitor;

public class False extends Exp {
    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }
}
