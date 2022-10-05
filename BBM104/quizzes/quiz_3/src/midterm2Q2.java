public class midterm2Q2 {

    public static void main(String[] args) throws ExceptionB{
        Number test = g(36);
        System.out.println(test);
    }

    public static double f(double num) throws ExceptionA, ExceptionB{
        if(num < 0){
            throw new ExceptionA();
        }
        else if(false){
            throw new ExceptionB();
        }
        else if(true){
            throw new ExceptionC();
        }

        return Math.sqrt(num);
    }

    public static Number g(double num) throws ExceptionB {
        try{
            return 2*f(num);
        }catch(ExceptionA excA){
            return -1;
        }catch(RuntimeException rte){
            return 1;
        }
    }
}

class ExceptionA extends Exception {}
class ExceptionB extends Exception {}
class ExceptionC extends RuntimeException {}