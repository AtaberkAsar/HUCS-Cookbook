class X {
    public int val;
    public void calc(){
        val += 5;
    }
}

class Y extends X {
    public void calc(){
        val += 2;
    }
    public void calc(int num){
        calc();
        super.calc();
        val *= num;
    }
}

class Z extends Y {
    public void calc(double num){
        calc((int) num);
        val += num;
    }
}

public class midterm2022Q3 {
    public static void main(String[] args){
        Z test = new Z();
        test.calc(3);
        System.out.println(test.val);
        test.calc(3.0);
        System.out.println(test.val);
    }
}

