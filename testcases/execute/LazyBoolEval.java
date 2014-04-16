class LazyBoolEval {
    public static void main(String[] args) {
    	boolean b;
    	Other o;
    	o = new Other();
    	b = 1 < 0 || o.getTrue();
    	System.out.println(1);
    }
}

class Other {
	public boolean getTrue() {
		System.out.println(2);

		return true;
	}
}