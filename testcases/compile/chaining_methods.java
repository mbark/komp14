class Main {
    public static void main(String[] args) {
    	Main m;
    	m = m.getMain().getMain();
    }
}

class Other {
    public Main getMain() {
    	return new Main();
    }
}
