public class Company extends Properties{

    public Company(int id, String name, int cost){
        super(id, name, cost);
    }

    @Override
    public void setRent(int rent, int dice){
        rent = 4 * dice;
        super.setRent(rent, 0);
    }

}
