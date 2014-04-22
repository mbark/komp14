class WrongNumberOfParams {
    public static void main(String[] args) {
        int a;
        a = (new A()).A(1);
    }
}

class A {
    public int A(int a, int b) {
        return 1;
    }
}
