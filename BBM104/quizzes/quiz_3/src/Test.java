import java.io.IOException;

public class Test {
    public void print(){
        System.out.println("Compiled....");
    }
}

class Fire {
    Fire(){
        System.out.println("Fire Constructor");
    }
    Fire(int x){
        System.out.println("Fire int Constructor");
    }
}

class Water extends Fire {
    Water(){
        System.out.println("Water Constructor");
    }
    Water(int x){
        System.out.println("Water int Constructor");
    }
}

class Earth extends Water{
    Earth(){
        System.out.println("Earth Constructor");
    }
    Earth(int x){
        System.out.println("Earth int Constructor");
    }
}

class Air extends Fire{
    Air(){
        System.out.println("Air Constructor");
    }
    Air(String x){
        System.out.println("Air String Constructor");
    }
}



abstract class MyTest extends Test{
    public static void main(String[] args) throws Exception{
        Fire fire = new Fire();
        Fire newFire = new Fire(4);
        System.out.println();

        Water water = new Water();
        Water newWater = new Water(45);
        System.out.println();

        Earth earth = new Earth();
        Earth newEarth = new Earth(90);
        System.out.println();

        Air air = new Air();
        Air newAir = new Air("test");
        System.out.println();




        try {
            throw new Exception();
        }catch(RuntimeException runtimeException){

        }catch(Exception exception){

        }

    }
}