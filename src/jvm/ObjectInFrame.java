package jvm;

public class ObjectInFrame implements frame.VMAccess {
    private String escapedName;
    public ObjectInFrame(String name, int offset, String signature) {
        n = name;
        o = offset;
        s = signature;
        escapedName = "\'" + n + "\'";
    }

    public String toString() {
        return "jvm.ObjectInFrame(" + o + ", " + n + ", " + s + ")";
    }

    public String declare() {
        return ".var " + o + " is " + escapedName + " " + s;
    }

    public String load() {
        switch (o) {
        case 0:
            return "    aload_0 ; " + escapedName;
        case 1:
            return "    aload_1 ; " + escapedName;
        case 2:
            return "    aload_2 ; " + escapedName;
        case 3:
            return "    aload_3 ; " + escapedName;
        default:
            return "    aload " + o + " ; " + escapedName;
        }
    }

    public String store() {
        switch (o) {
        case 0:
            return "    astore_0 ; " + escapedName;
        case 1:
            return "    astore_1 ; " + escapedName;
        case 2:
            return "    astore_2 ; " + escapedName;
        case 3:
            return "    astore_3 ; " + escapedName;
        default:
            return "    astore " + o + " ; " + escapedName;
        }
    }

    private int o;
    private String n;
    private String s;
}
