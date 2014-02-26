package tree;

public class ESEQ extends AbstractExp {
    public Stm stm;
    public AbstractExp exp;

    public ESEQ(Stm s, AbstractExp e) {
        stm = s;
        exp = e;
    }

    public ExpList kids() {
        throw new Error("kids() not applicable to ESEQ");
    }

    public AbstractExp build(ExpList kids) {
        throw new Error("build() not applicable to ESEQ");
    }
}
