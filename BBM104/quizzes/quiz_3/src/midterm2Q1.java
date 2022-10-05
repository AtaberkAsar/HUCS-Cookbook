public class midterm2Q1 {
    public static void main(String[] args) {
        X0 x = new X0();
        System.out.println(x instanceof B0);

        B0 b = x;
        System.out.println(b instanceof C0);

        // Y0 y = (Y0) b;
        // System.out.println(y instanceof B0);

        // System.out.println(x instanceof Y0);

//         Y0 y = x;
//         System.out.println(y instanceof B0);

        System.out.println();
        C0 myC = new C0();
        System.out.println(myC instanceof B0);
        System.out.println(myC instanceof X0);

    }
}

class B0 {}

class C0 extends B0{}

class X0 extends C0{
    public int a = 4;
}

class Y0 extends C0{}