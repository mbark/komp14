class Main {
    public static void main(String[] args) {
    	OtherClass c;
    	int i;
    	c = new OtherClass();
    	i = c.foo(this);
    }
}

class OtherClass {
	public int foo(Main m) {
		return 1;
	}
}