class Main {
    public static void main(String[] args) {
    	OtherClass o;
    }
}

class OtherClass {
	public int foo() {
		return new OtherClass();
	}
}