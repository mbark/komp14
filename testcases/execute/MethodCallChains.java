class MethodCallChains {
	public static void main(String[] args) {
		int a;
		int[] b;
		
		TM tm;

		a = new int[5].length;
		
		b = new int[235];

		a = b.length;
		a = 1;
		
		tm = new TM();

		a = tm.m1();

		b = tm.m2();
		a = tm.m2().length;
		a = 1;
		a = tm.m2()[a+1+a];

		b = tm.m3(1).m2();
		a = tm.m3(1).m2().length;
		a = 1;
		a = tm.m3(1).m2()[1+a+a];

		b = tm.m3(1).m3(a).m2();
		a = tm.m3(1).m3(a).m2().length;
		a = 1;
		a = tm.m3(1).m3(a).m2()[1+a+a];
		
		System.out.println(a);
	}
}

class TM {
	
	int a;
	int[] b;

	public int m1()
	{
		int c;
		c = 1;
		a = c;
		return c;
	}

	public int[] m2()
	{
		int[] d;
		d = new int[4];
		d[3] = 123;
		b = d;
		return d;
	}	

	public TM m3(int x)
	{
		TM e;
		e = new TM();
		return e;
	}
}

