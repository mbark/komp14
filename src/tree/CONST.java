package tree;

public class CONST extends AbstractExp {
    public int value;

    public CONST(int v) {
        value = v;
    }

    public ExpList kids() {
        return null;
    }

    public AbstractExp build(ExpList kids) {
        return this;
    }
}
