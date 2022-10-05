public class finalQ1 {

    public static void main(String[] args){
        Object o = new NewAnimal();
        NewAnimal a = new Mammal();
        Mammal m = new Cat();
        Cat d = new Bengal();

        m.method1();
        System.out.println();
        m.method2();
        System.out.println();
        //m.method3();
        System.out.println();
        System.out.println("********************************");
        System.out.println();
        d.method1();
        System.out.println();
        System.out.println("************************************");
        System.out.println();
        d.method2();
        System.out.println();
        d.method3();
        System.out.println();
        System.out.println("******************************************");
        System.out.println();
        a.method1();
        System.out.println();
        System.out.println("****************************************");
        System.out.println();
        a.method2();
        System.out.println();
        //a.method3();
        System.out.println();
        ((NewAnimal) o).method1();
        System.out.println();
        ((Mammal) o).method2();
        System.out.println();


    }
}


class NewAnimal {
    public void method1(){
        int x = 2;
        System.out.print("Animal"+(--x));
    }
    public void method2(){
        int y = 4;
        System.out.print("Animal"+(Math.sqrt(y)));
    }
}

class Cat extends Mammal{
    public void method1(){
        int t = 3;
        System.out.print("Cat" + (t++));
    }
    public void method3(){
        int q = 5;
        System.out.print("Cat" + ((q--) * 2));
        method1();
    }
}

class Bengal extends Cat{
    public void method1(){
        int g=7;
        System.out.print("Bengal"+Math.pow(g++, (1.0/3)));
        super.method1();
    }
    public void method2(){
        int h = 16;
        System.out.print("Bengal"+Math.pow(h,1.0/4));
        super.method2();
    }
}

class Mammal extends NewAnimal {
    public void method2(){
        int q = 3;
        method1();
        System.out.print("Mammal"+(q--));
    }
    public void method1(){
        int k = 1;
        System.out.print("Mammal"+ k+2);
    }
}











