class WrongType7 {
    public static void main(String[] args) {
        OtherClass m;
        Double s;
        m = new OtherClass();
        s = m.foo();
    }
}

class OtherClass {
    public int foo() {
        return 1;
    }
}
