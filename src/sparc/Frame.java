package sparc;

import java.util.List;

import temp.Label;
import temp.Temp;
import temp.TempList;
import temp.TempMap;
import tree.CALL;
import tree.CONST;
import tree.EXP;
import tree.Exp;
import tree.ExpList;
import tree.MOVE;
import tree.NAME;
import tree.SEQ;
import tree.Stm;
import tree.TEMP;

/**
 * An implementation of the Frame interface for the Sun processor architecture
 * (SPARC).
 * 
 * @see frame.Frame
 */
public class Frame implements frame.Frame {
    private int nextFormal = 0;
    private int nextLocal = 0;
    // There is always room for six outgoing parameters in the frame.
    private int maxOutgoing = 6;
    private Label name;
    private Stm moveIncoming = new EXP(new CONST(0)); // NOP

    public Frame(Label n, List<Boolean> escapes) {
        name = n;
        int numformals = escapes.size();
        /* Implement allocation of access objects using allocFormal */
    }

    /* Must be implemented */
    public List<frame.Access> formals() {
        return null;
    }

    public String toString() {
        return "sparc.Frame(" + name() + ")";
    }

    public Label name() {
        return name;
    }

    public int size() {
        // In the SPARC architecture, 16 words are reserved for
        // possible dumps of the register window and 1 word is
        // reserved for the return of aggregated types (structs in C).
        // Apart from that, the frame must be able to hold all
        // local variables and all outgoing parameters. The first
        // six outgoing parameters are per default passed in registers,
        // but a function may write any of its incoming parameters
        // to the frame if that is necessary. Hence, we must always
        // make room for six outgoing parameters in the frame.
        int s = Hardware.wordSize * (16 + 1 + maxOutgoing + nextLocal);
        while (s % (2 * Hardware.wordSize) != 0) {
            s += Hardware.wordSize;
        }
        return s;
    }

    // We could get better performance by putting all non-escaping
    // formal and local variables in registers and then let the
    // register allocator take care of the trouble. But then we might
    // have to implement spilling in the register allocator.
    // So instead we only put the first six formal variables
    // in registers (since they are in registers anyway according
    // to the SPARC calling conventions).
    private frame.Access allocFormal(boolean escape) {
        frame.Access a;
        if (escape || nextFormal >= 6) {
            a = new InFrame(Hardware.wordSize * (16 + 1 + nextFormal));
        } else {
            a = new InReg(new Temp());
        }
        if (nextFormal < 6) {
            moveIncoming = new SEQ(new MOVE(a.exp(new TEMP(Hardware.FP)),
                    new TEMP(Hardware.getInReg(nextFormal))), moveIncoming);
        }
        nextFormal++;
        return a;
    }

    public frame.Access allocLocal(boolean escape) {
        return new InFrame(-Hardware.wordSize * (++nextLocal));
    }

    public frame.Access accessOutgoing(int index) {
        // Reserve room in the frame for all outgoing parameters
        if (maxOutgoing < index) {
            maxOutgoing = index;
        }
        // The first six outgoing parameters are in registers.
        // The remaining ones are on the stack but the are
        // accessed relative to the stack pointer. Hence, they
        // get the same offset as incoming parameters.
        if (index < 6) {
            return new InReg(Hardware.getOutReg(index));
        } else {
            return new InFrame(Hardware.wordSize * (16 + 1 + index));
        }
    }

    public Exp externalCall(String func, ExpList args) {
        return new CALL(new NAME(new Label(func)), args);
    }

    public Stm procEntryExit1(Stm body) {
        return new SEQ(moveIncoming, body);
    }

    /* Must be impemented */
    public List<assem.Instr> procEntryExit2(List<assem.Instr> inst) {
        return null;
    }

    /* Must be impemented */
    public frame.Proc procEntryExit3(List<assem.Instr> body) {
        return null;
    }

    public Temp RV() {
        return Hardware.RV;
    }

    public Temp FP() {
        return Hardware.FP;
    }

    public int wordSize() {
        return Hardware.wordSize;
    }

    /* Must be implemented */
    public List<assem.Instr> codegen(tree.Stm stm) {
        return null;
    }

    /* Must be implemented */
    public TempMap initial() {
        return null;
    }

    /* Must be implemented */
    public TempList registers() {
        return null;
    }
}
