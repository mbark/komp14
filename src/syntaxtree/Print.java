package syntaxtree;

import tree.Stm;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class Print extends Statement {
    public Exp e;

    public Print(Exp ae) {
        e = ae;
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    public Stm accept(TreeVisitor v) {
        return v.visit(this);
    }
}
