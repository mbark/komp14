class TwoMethodsSameParam {
    public static void main(String[] args) {
    }
}

class Foo {
    public int foo(Foo args) {
        return 1;
    }

    public int bar(Foo args) {
        return 0;
    }
}
