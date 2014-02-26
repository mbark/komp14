class Main {
    public static void main(String[] args) {
        Aux aux;
        int[] a;
        aux = new Aux();
        a = new int[10];
        aux = aux.doTheThing(a);
        System.out.println(0);
    }
}

class Aux {
    boolean foo;

    public Aux doTheThing(int[] arr)
    {
        int i;
        i = 0;
        while(i < 0)
        {
            if(!true)
            {
                arr[i] = 1;
            }
            else
            {
            }
        }
        return this;
    }
}
