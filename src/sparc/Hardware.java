package sparc;

import temp.Temp;

public class Hardware
{
    static final int wordSize = 4;

    private static final Temp[] inRegs = {
	new Temp(), new Temp(), new Temp(), new Temp(),
	new Temp(), new Temp(), new Temp(), new Temp()
    };

    private static final Temp[] localRegs = {
	new Temp(), new Temp(), new Temp(), new Temp(),
	new Temp(), new Temp(), new Temp(), new Temp()
    };

    private static final Temp[] globalRegs = {
	new Temp(), new Temp(), new Temp(), new Temp(),
	new Temp(), new Temp(), new Temp(), new Temp()
    };

    private static final Temp[] outRegs = {
	new Temp(), new Temp(), new Temp(), new Temp(),
	new Temp(), new Temp(), new Temp(), new Temp()
    };

    // specialregs: %i0 ; %i6 -- %i7 ; %o6 -- %o7
    // argregs:     %o0 -- %o5
    // callersaves: %i1 -- %i5 ; %l0 -- %l7
    // globalregs:  %g0 -- %g7  (%g0 is ZERO register)

    static final Temp RV   = inRegs[0];
    static final Temp FP   = inRegs[6];
    static final Temp SP   = outRegs[6];
    static final Temp RA   = inRegs[7];
    static final Temp THIS = inRegs[0];
    static final Temp ZERO = globalRegs[0];


    static Temp getInReg(int i) {
	if (i < 0 || i >= inRegs.length) {
	    throw new error.InternalError(
		"Attempt to access non-existent in register #" + i + ".");
	}
	return inRegs[i];
    }
    static Temp getOutReg(int i) {
	if (i < 0 || i >= outRegs.length) {
	    throw new error.InternalError(
		"Attempt to access non-existent out register #" + i + ".");
	}
	return outRegs[i];
    }
    

    static Temp getLocalReg(int i) {
	if (i < 0 || i >= localRegs.length) {
	    throw new error.InternalError(
		"Attempt to access non-existent local register #" + i + ".");
	}
	return localRegs[i];
    }


    static Temp getGlobalReg(int i) {
	if (i < 0 || i >= localRegs.length) {
	    throw new error.InternalError(
		"Attempt to access non-existent global register #" + i + ".");
	}
	return globalRegs[i];
    }

}
