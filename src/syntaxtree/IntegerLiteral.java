package syntaxtree;

import tree.AbstractExp;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class IntegerLiteral extends Exp {
    public int i;

    public IntegerLiteral(int ai) {
        i = ai;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public AbstractExp accept(TreeVisitor v) {
        return v.visit(this);
    }
}
