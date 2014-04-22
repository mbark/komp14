class ChainingMethods {
    public static void main(String[] args) {
        Other o;
        o = o.getOther();
        o = o.getOther().getOther();
    }
}

class Other {
    public Other getOther() {
        return new Other();
    }
}
