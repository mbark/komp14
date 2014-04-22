class WrongNumberOfParams {
    public static void main(String[] args) {
        Other o;
        int a;
        o = new Other();
        a = o.do(1);
        a = o.do(1, 2, 3);
    }
}

class Other {
    public int do(int a, int b) {
        return 1;
    }
}
