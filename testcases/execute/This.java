class This {
    public static void main(String[] args) {
        int a;
        Other o;

        o = new Other();
        a = o.doA();

        System.out.println(a);
    }
}

class Other {
    public int doA() {
        return this.doB();
    }

    public int doB() {
        return 2;
    }
}
