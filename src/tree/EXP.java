package tree;

public class EXP extends Stm {
    public AbstractExp exp;

    public EXP(AbstractExp e) {
        exp = e;
    }

    public ExpList kids() {
        return new ExpList(exp);
    }

    public Stm build(ExpList kids) {
        return new EXP(kids.head);
    }
}
