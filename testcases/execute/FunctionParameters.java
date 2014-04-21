class FunctionParameters {
	public static void main(String[] args) {
		AClass f;
		int i;
		f = new AClass();
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
}
