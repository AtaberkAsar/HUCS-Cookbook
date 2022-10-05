class A {
    public A() {
        m1();
    }
    public void m1() {
        System.out.print("a good ");
    }
    public void m2() {
        System.out.print("as a ");
    }

    public static void main(String[] args) {
        A[] as = new A[] {new B(), new C(), new D()};
    }
}

class B extends A {
    public B() {
        System.out.print("cook ");
    }
}

class C extends B {
    public C() {
        System.out.print("as much ");
    }

    public void m1() {
        System.out.print("could ");
    }

    public void m2() {
        System.out.print("cookies as ");
    }
}

class D extends B {
    public D() {
        System.out.print("who could ");
        m2();
    }

    public void m1() {
        System.out.print("cookies as ");
        super.m1();
    }

    public void m2() {
        System.out.print("cook cookies ");
    }
}


