class MethodCallOrdering {
    public static void main(String[] args) {
        int a;
        boolean b;
        Other o;
        o = new Other();

        a = o.init();
        b = o.doA() && o.doB();

        System.out.println(o.getA());
    }
}

class Other {
    int a;
    int b;

    public int init() {
        a = 3;
        b = 3;
        return a;
    }

    public boolean doA() {
        b = 0;

        return true;
    }

    public boolean doB() {
        a = a - b;

        return true;
    }

    public int getA() {
        return a;
    }
}
