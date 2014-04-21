class FieldsTest {
	public static void main(String[] args) {
		Fields f;
		int i;
		f = new Fields();
		i = f.doStuff(3);
		System.out.println(i);
	}
}

class Fields {
	int[] array;
	int i;

	public int doStuff(int a) {
		a = 2;
		i = 3;
		return 0;
	}
}
