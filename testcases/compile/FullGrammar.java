/**
 * Test case intended to exercise all grammar constructs.
 */
class FullGrammar {
    public static void main(String[] args) {
        Aux aux;
        int[] a;
        aux = new Aux();
        a = new int[10]; // not used
        aux = aux.doTheThing(a);
        System.out.println(0);
    }
}

class Aux {
    boolean foo;

    // Nonsense method
    public Aux doTheThing(int[] arr)
    {
        int i;
        Aux aux;
        boolean b;

        i = 0;
        while(i < 0)
        {
            if(!true)
            {
                b = arr[arr.length] + 9 < 17 && !(arr[0] * 7 < 1717);
                arr[i] = 4711;
            }
            else
            {
            }
        }
        aux = new Aux();
        aux = aux.doTheThing(arr);

        return this;
    }
}
