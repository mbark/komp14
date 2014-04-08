package syntaxtree;

import tree.AbstractExp;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public abstract class Type {

    public abstract void accept(Visitor v);

    public abstract Type accept(TypeVisitor v);

    public abstract AbstractExp accept(TreeVisitor v);

    @Override
    public String toString() {
        return getClass().getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return getClass().equals(obj.getClass());
    }

}
