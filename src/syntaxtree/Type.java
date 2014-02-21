package syntaxtree;

import visitor.TypeVisitor;
import visitor.Visitor;

public abstract class Type {
    public boolean equals(Type tp) {
        return getClass().equals(tp.getClass());
    }

    public abstract void accept(Visitor v);

    public abstract Type accept(TypeVisitor v);
}
