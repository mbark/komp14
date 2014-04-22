class DeclarationOutOfPlace {
    public static void main(String[] args) {
    }
}

class Foo {
    public int foo() {
        int args;

        while(true) {
            System.out.println("foo");
        }

        int bar;

        return 2;
    }
}
