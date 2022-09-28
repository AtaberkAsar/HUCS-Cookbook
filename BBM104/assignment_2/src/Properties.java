public class Properties extends Squares{
    private final int cost;
    private boolean payable = true;
    private int rent;
    private String owner;

    public Properties(int id, String name, int cost){
        super(id, name);
        this.cost = cost;
    }

    public void setPayable(String owner){
        payable = false;
        this.owner = owner;
    }

    /**
     *
     * @param rent int rent value, needed for lands
     * @param factor int factor value, dice result for company, railroad count of opponent player for railroads
     */
    public void setRent(int rent, int factor) {
        this.rent = rent;
    }

    public int getCost(){
        return cost;
    }

    public boolean getPayable(){
        return payable;
    }

    public int getRent() {
        return rent;
    }

    public String getOwner() {
        return owner;
    }
}
