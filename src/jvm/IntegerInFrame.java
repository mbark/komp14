package jvm;

public class IntegerInFrame implements frame.VMAccess {
    private String escapedName;
    
    public IntegerInFrame(String name, int offset, String signature) {
        n = name;
        o = offset;
        s = signature;
        escapedName = "\'" + n + "\'";
    }

    public String toString() {
        return "jvm.IntegerInFrame(" + o + ", " + n + ", " + s + ")";
    }

    public String declare() {
        return ".var " + o + " is " + escapedName + " " + s;
    }

    public String load() {
        switch (o) {
        case 0:
            return "    iload_0 ; " + escapedName;
        case 1:
            return "    iload_1 ; " + escapedName;
        case 2:
            return "    iload_2 ; " + escapedName;
        case 3:
            return "    iload_3 ; " + escapedName;
        default:
            return "    iload " + o + " ; " + escapedName;
        }
    }

    public String store() {
        switch (o) {
        case 0:
            return "    istore_0 ; " + escapedName;
        case 1:
            return "    istore_1 ; " + escapedName;
        case 2:
            return "    istore_2 ; " + escapedName;
        case 3:
            return "    istore_3 ; " + escapedName;
        default:
            return "    istore " + o + " ; " + escapedName;
        }
    }

    private int o;
    private String n;
    private String s;
}
