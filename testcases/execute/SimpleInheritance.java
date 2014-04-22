// EXT:ICG

class SimpleInheritance {
    public static void main(String[] args)
    {
        C c;
        c = new C();
        System.out.println(c.foo());
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
