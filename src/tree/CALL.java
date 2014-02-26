package tree;

public class CALL extends AbstractExp {
    public AbstractExp func;
    public ExpList args;

    public CALL(AbstractExp f, ExpList a) {
        func = f;
        args = a;
    }

    public ExpList kids() {
        return new ExpList(func, args);
    }

    public AbstractExp build(ExpList kids) {
        return new CALL(kids.head, kids.tail);
    }

}
