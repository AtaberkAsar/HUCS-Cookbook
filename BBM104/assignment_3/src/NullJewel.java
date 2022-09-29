import java.util.ArrayList;

public class NullJewel extends Jewel{

    @Override
    public void popJewel(ArrayList<ArrayList<Jewel>> gameMap, int y, int x) throws NullJewelException{
        throw new NullJewelException();
    }

    @Override
    public String toString() {
        return " ";
    }
}

class NullJewelException extends Exception{}