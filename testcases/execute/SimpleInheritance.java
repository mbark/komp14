// EXT:ISC

class SimpleInheritance {
    public static void main(String[] args)
    {
        C c;
        c = new C();
        System.out.println(c.foo());
        System.out.println(c.bar());
        System.out.println(c.baz());
    }
}

class A {
    public int foo() {
        return 2;
    }
}

class B extends A {
    public int bar() {
        return this.foo() + 3;
    }
}

class C extends B {
    public int baz() {
        return this.bar() + 5;
    }
}
