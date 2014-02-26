package tree;

public class ExpList {
    public AbstractExp head;
    public ExpList tail;

    public ExpList(AbstractExp h, ExpList t) {
        head = h;
        tail = t;
    }

    public ExpList(AbstractExp h) {
        head = h;
        tail = null;
    }

    public ExpList(AbstractExp e1, AbstractExp e2) {
        head = e1;
        tail = new ExpList(e2);
    }
}
