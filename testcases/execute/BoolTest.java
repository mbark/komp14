class BoolTest {
    public static void main(String[] args) {
        Class c;
        c = new Class();

        System.out.println(c.eq(1, 1));
        System.out.println(c.eq(0, 1));
        System.out.println(c.eq(1, 0));

        System.out.println(c.lt(1, 1));
        System.out.println(c.lt(0, 1));
        System.out.println(c.lt(1, 0));

        System.out.println(c.gte(1, 1));
        System.out.println(c.gte(0, 1));
        System.out.println(c.gte(1, 0));
    }
}

class Class {
    public int eq(int a, int b) {
        int r;

        if(!(a < b) && !(b < a)) {
            r = 1;
        } else {
            r = 0;
        }

        return r;
    }

    public int gte(int a, int b) {
        int r;

        if(!(a < b)) {
            r = 1;
        } else {
            r = 0;
        }

        return r;
    }

    public int lt(int a, int b) {
        int r;

        if(a < b) {
            r = 1;
        } else {
            r = 0;
        }

        return r;
    }
}
