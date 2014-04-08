package syntaxtree;

import tree.AbstractExp;
import visitor.JVMVisitor;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class Times extends Exp {
    public Exp e1, e2;

    public Times(Exp ae1, Exp ae2) {
        e1 = ae1;
        e2 = ae2;
    }

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
        return new IntegerType();
    }
}
