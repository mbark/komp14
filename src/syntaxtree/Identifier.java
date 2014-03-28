package syntaxtree;

import mjc.SourceLocation;
import mjc.Token;
import tree.AbstractExp;
import visitor.TreeVisitor;
import visitor.TypeVisitor;
import visitor.Visitor;

public class Identifier {
    public String s;
    public SourceLocation sourceLocation;

    public Identifier(String as) {
        s = as;
    }

    public Identifier(Token t) {
        s = t.toString();
        sourceLocation = new SourceLocation(t.beginLine, t.beginColumn,
                t.endLine, t.endColumn);
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

    public String toString() {
        return s;
    }

}
