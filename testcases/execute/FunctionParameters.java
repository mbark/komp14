class FunctionParameters {
    public static void main(String[] args) {
        AClass f;
        int i;
        int[] array;
        array = new int[5];
        f = new AClass();
        i = f.doStuff3(array);
        i = f.doStuff2(10, f, 5);
        i = f.doStuff(2);
        System.out.println(i);
    }
}

class AClass {
    public int doStuff(int i) {
        return i;
    }

    public int doStuff2(int i, AClass c, int j) {
        j = 3;
        i = j;
        return c.doStuff(j);
    }

    public int doStuff3(int[] a) {
        return 0;
    }
}
