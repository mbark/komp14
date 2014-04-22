class LazyBoolEval {
    public static void main(String[] args) {
        boolean b;
        Other o;
        o = new Other();
        b = o.doB() && o.doA() && o.doB() && o.doB() && o.doB() && o.doB();
    }
}

class Other {
    public boolean doA() {
        System.out.println(1);
        return false;
    }

    public boolean doB() {
        System.out.println(2);
        return true;
    }
}
