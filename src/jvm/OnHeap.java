package jvm;

public class OnHeap implements frame.VMAccess {
    private String escapedName;
    public OnHeap(String className, String fieldName, String signature) {
        c = className;
        f = fieldName;
        s = signature;
        escapedName = "\'" + f + "\'";
    }

    public String toString() {
        return "jvm.OnHeap(" + c + ", " + escapedName + ", " + s + ")";
    }

    public String declare() {
        return ".field public " + escapedName + " " + s;
    }

    public String load() {
        return "    aload_0 ; this\n" +
               "    getfield " + c + "/" + f + " " + s;
    }

    // We get one extra instruction here, since the arguments
    // end up in wrong order on the stack.
    public String store() {
        return "    aload_0 ; this\n" +
               "    swap\n" +
               "    putfield " + c + "/" + f + " " + s;
    }

    private String c;
    private String f;
    private String s;
}
