class A2 {
    public static int x;
    public A2(){ x=6;}
    public String toString(){
        return Integer.toString(x);
    }
}

class B2 extends A2 {
    public B2(){ x=10;}
}

public class Main{
    public static void main(String[] args){
        A2 a2 = new A2();
        System.out.print(a2);
        a2.x=0;
        System.out.print(a2);
        B2 b2 = new B2();
        System.out.print(a2);
        System.out.print(b2);
        System.out.println(A2.x);
        b2.x=5;
        System.out.println(b2);
        System.out.println(b2.x);
    }
}

