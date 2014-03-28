package syntaxtree;

import tree.Stm;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class MainClass {
    public Identifier i1, i2;
    public VarDeclList vl;
    public Statement s;

    public MainClass(Identifier ai1, Identifier ai2, VarDeclList avl,
            Statement as) {
        i1 = ai1;
        i2 = ai2;
        vl = avl;
        s = as;
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
