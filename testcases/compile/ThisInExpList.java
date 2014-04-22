class ThisInExpList {
    public static void main(String[] args) {
        OtherClass c;
        int i;
        c = new OtherClass();
        i = c.foo();
    }
}

class OtherClass {
    public int bar(OtherClass o) {
        return 1;
    }

    public int foo() {
        return this.bar(this);
    }
}
