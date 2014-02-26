package sparc;

import temp.Temp;
import tree.AbstractExp;
import tree.TEMP;

public class InReg implements frame.Access {
    private Temp reg;

    public InReg(Temp r) {
        reg = r;
    }

    public String toString() {
        return "sparc.InReg(" + reg.toString() + ")";
    }

    public AbstractExp exp(AbstractExp basePointer) {
        return new TEMP(reg);
    }
}
