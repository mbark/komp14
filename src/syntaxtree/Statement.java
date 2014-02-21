package syntaxtree;

import visitor.TypeVisitor;
import visitor.Visitor;

public abstract class Statement {
    public abstract void accept(Visitor v);

    public abstract Type accept(TypeVisitor v);
}
