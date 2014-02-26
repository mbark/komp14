package tree;

public class MEM extends AbstractExp {
    public AbstractExp exp;

    public MEM(AbstractExp e) {
        exp = e;
    }

    public ExpList kids() {
        return new ExpList(exp);
    }

    public AbstractExp build(ExpList kids) {
        return new MEM(kids.head);
    }
}
