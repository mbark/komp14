class Bogus5 {
    public static void main(String[] args) {
        int a;
        a = (new A()).A(1); // fel antal params
    }
}

class A {
    public int A(int a, int b) {
        return 1;
    }
}