public class getFieldFromSubfield {
    public static void main(String[] args) {
        Mahmut mahmut = new Ahmet();
        System.out.println(mahmut.x);
        System.out.println(mahmut.getX());
        System.out.println(((Ahmet) mahmut).getX());
    }
}

class Mahmut{
    int x = 5;

    public int getX() {
        return x;
    }
}

class Ahmet extends Mahmut{
    Ahmet(){
        x = 4;
    }
}

class Mehmet extends Mahmut {
    int x = 7;
}
