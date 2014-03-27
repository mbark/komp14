class Main {
    public static void main(String[] args) {
        OtherClass c;
        int i;
        c = new OtherClass();
        i = c.foo();
    }
}

class OtherClass {
    public int foo() {
        return 1;
    }
}