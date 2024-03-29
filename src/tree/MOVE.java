package tree;

public class MOVE extends Stm {
    public AbstractExp dst, src;

    public MOVE(AbstractExp d, AbstractExp s) {
        dst = d;
        src = s;
    }

    public ExpList kids() {
        if (dst instanceof MEM) {
            return new ExpList(((MEM) dst).exp, src);
        } else {
            return new ExpList(src);
        }
    }

    public Stm build(ExpList kids) {
        if (dst instanceof MEM) {
            return new MOVE(new MEM(kids.head), kids.tail.head);
        } else {
            return new MOVE(dst, kids.head);
        }
    }
}
