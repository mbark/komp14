package jvm;

import syntaxtree.FormalList;
import syntaxtree.Type;

public class Factory implements frame.VMFactory {
    public frame.VMFrame newFrame(String methodName, FormalList formals,
            Type returnType) {
        return new jvm.Frame(methodName, formals, returnType);
    }

    public frame.VMRecord newRecord(String name) {
        return new jvm.Record(name);
    }

}
