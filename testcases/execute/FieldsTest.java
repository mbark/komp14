class FieldsTest {
    public static void main(String[] args) {
        Fields f;
        int i;
        f = new Fields();
        i = f.set();
        i = f.get();
        i = f.doStuff(3);
        System.out.println(i);
    }
}

class Fields {
    int[] array;
    int i;

    public int doStuff(int a) {
        boolean i;
        i = true;
        a = 2;
        return 0;
    }

    public int set() {
        i = 1;
        return i;
    }

    public int get() {
        return i;
    }
}
