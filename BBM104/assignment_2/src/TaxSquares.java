public class TaxSquares extends Squares{
    private final int cost;

    public TaxSquares(int id, String name){
        super(id, name);
        this.cost = 100;
    }

    public int getCost() {
        return cost;
    }

}
