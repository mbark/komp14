package tree;

public class TEMP extends AbstractExp {
    public temp.Temp temp;

    public TEMP(temp.Temp t) {
        temp = t;
    }

    public ExpList kids() {
        return null;
    }

    public AbstractExp build(ExpList kids) {
        return this;
    }
}
