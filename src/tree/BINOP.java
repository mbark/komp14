package tree;

public class BINOP extends Exp {
    public int binop;
    public Exp left, right;

    public BINOP(int b, Exp l, Exp r) {
        binop = b;
        left = l;
        right = r;
    }

    public final static int PLUS = 0;
    public final static int MINUS = 1;
    public final static int MUL = 2;
    public final static int DIV = 3;
    public final static int AND = 4;
    public final static int OR = 5;
    public final static int LSHIFT = 6;
    public final static int RSHIFT = 7;
    public final static int ARSHIFT = 8;
    public final static int XOR = 9;

    public ExpList kids() {
        return new ExpList(left, new ExpList(right));
    }

    public Exp build(ExpList kids) {
        return new BINOP(binop, kids.head, kids.tail.head);
    }
}
