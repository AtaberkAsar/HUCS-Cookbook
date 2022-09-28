public class Land extends Properties{

    public Land(int id, String name, int cost){
        super(id, name, cost);
    }

    @Override
    public void setRent(int rent, int factor) {
        if(getCost() <= 2000){
            rent = (int) (0.4 * getCost());
        }
        else if(getCost() <= 3000){
            rent = (int) (0.3 * getCost());
        }
        else{
            rent = (int) (0.35 * getCost());
        }
        super.setRent(rent, 0);
    }

}
