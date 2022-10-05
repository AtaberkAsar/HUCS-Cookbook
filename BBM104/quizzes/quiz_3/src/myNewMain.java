
class Anan {
    private int f1=1;
    int f2=2;
    protected int f3=3;
    public int f4=4;
}
class Baban extends Anan{
    Anan a= new Anan();
    public int i =a.f4;
    public void baslat(){System.out.println(i); System.out.println(f2);}
}
class R {
    int i = new Anan().f4;
    public void baslat(){System.out.println(i);}
}

public class myNewMain {

    public static void main(String[] args) {

        Anan aa=new Anan();
        Baban bb=new Baban();
        R rr=new R();


        bb.baslat();
        rr.baslat();

    }
}
