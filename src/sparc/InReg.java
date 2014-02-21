package sparc;

import temp.Temp;
import tree.Exp;
import tree.TEMP;

public class InReg implements frame.Access {
    private Temp reg;

    public InReg(Temp r) {
        reg = r;
    }

    public String toString() {
        return "sparc.InReg(" + reg.toString() + ")";
    }

    public Exp exp(Exp basePointer) {
        return new TEMP(reg);
    }
}
