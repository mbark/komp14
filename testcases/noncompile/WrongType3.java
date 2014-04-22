class WrongType3 {
    public static void main(String[] args) {
        OtherClass m;
        int i;
        m  = new OtherClass();
        i = m.foo().foo();
    }
}

class OtherClass {
    public int foo() {
        return 1;
    }
}
