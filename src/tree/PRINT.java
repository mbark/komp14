package tree;

public class PRINT extends Stm {
    public AbstractExp e;

    public PRINT(AbstractExp e) {
        this.e = e;
    }

    @Override
    public ExpList kids() {
        return new ExpList(e);
    }

    @Override
    public Stm build(ExpList kids) {
        return new PRINT(e);
    }
}
