class DoubleDeclaredMethods {
    public static void main(String[] args) {
    }
}

class DoubleDeclaredMethods {
    public int foo() {
        return 1;
    }

    public int foo() {
        return 2;
    }
}
