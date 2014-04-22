class ArrayTest {
    public static void main(String[] args) {
        int[] array;
        int i;
        OtherClass c;

        c = new OtherClass();
        array = new int[5];
        array = c.doStuff(array);

        i = 0;
        while(i < array.length) {
            System.out.println(array[i]);
            i = i + 1;
        }
    }
}

class OtherClass {
    public int[] doStuff(int[] array) {
        int i;
        int j;
        i = 1;
        j = 3;

        array[0] = 1;

        array[i-1] = 0;
        array[i] = 0;
        array[i+1] = 0;
        array[j*i] = 0;
        array[i+j] = 0;

        if(array[i-1] < 0) {
            array[i-1] = 1;
        } else {
            array[i-1] = 0;
        }

        return array;
    }
}
