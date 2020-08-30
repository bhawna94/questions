
class A {
    B b;
}

class B {
    C c;
    C a;
}

class C {
    B b;
}

public class ExampleOne {

    public static void main(String[] args) {

        A a = new A();
        C c = new C();
        a.b.c = c.b.a;

    }
}
