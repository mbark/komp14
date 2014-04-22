class MathBinding {
    public static void main(String[] args) {
        int a;
        a = 2 + 2 * 2 + 2;
        System.out.println(a);

        a = (2 + 2) * 2 + 2;
        System.out.println(a);

        a = 2 + 2 * 2 - 2 + 2;
        System.out.println(a);

        a = 2 + 2 * (2 - 2) + 2;
        System.out.println(a);

        a = 2 + 2 * (2 - 2 + 2);
        System.out.println(a);

        a = (((2 + ((2) * 2) - (2 + 2))));
        System.out.println(a);
    }
}
