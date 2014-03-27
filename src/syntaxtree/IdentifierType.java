package syntaxtree;

import mjc.Token;
import visitor.TypeVisitor;
import visitor.Visitor;

public class IdentifierType extends Type {
    public String s;

    public boolean equals(Type tp) {
        if (!(tp instanceof IdentifierType)) {
            return false;
        }
        return ((IdentifierType) tp).s.equals(s);
    }

    public IdentifierType(String as) {
        s = as;
    }

    public IdentifierType(Token t) {
        s = t.toString();
    }

    public void accept(Visitor v) {
        v.visit(this);
    }

    public Type accept(TypeVisitor v) {
        return v.visit(this);
    }

    @Override
    public boolean equals(Object obj) {
        boolean isEqual = super.equals(obj);
        if (isEqual) {
            IdentifierType other = (IdentifierType) obj;
            if (other.s != null) {
                return other.s.equals(this.s);
            } else {
                return this.s == null;
            }
        }

        return false;
    }
    
    @Override
    public String toString() {
        return this.getClass().getName() + "(" + this.s + ")";
    }
}
