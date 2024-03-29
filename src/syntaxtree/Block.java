package syntaxtree;

import tree.Stm;
import visitor.JVMVisitor;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class Block extends Statement {
    public StatementList sl;

    public Block(StatementList asl) {
        sl = asl;
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

    public Stm accept(TreeVisitor v) {
        return v.visit(this);
    }
}
