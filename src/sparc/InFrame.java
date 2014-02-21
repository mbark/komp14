package sparc;

import tree.BINOP;
import tree.CONST;
import tree.Exp;
import tree.MEM;

class InFrame implements frame.Access {
    private int offset;

    public InFrame(int o) {
        offset = o;
    }

    public String toString() {
        return "sparc.InFrame(" + offset + ")";
    }

    public Exp exp(Exp basePointer) {
        // A small optimization for the special case offset == 0.
        // (This case occurs frequently with heap objects.)
        if (offset == 0) {
            return new MEM(basePointer);
        } else {
            return new MEM(
                    new BINOP(BINOP.PLUS, basePointer, new CONST(offset)));
        }
    }

}
