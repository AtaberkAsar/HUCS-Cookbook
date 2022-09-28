public class RailRoads extends Properties{

    public  RailRoads(int id, String name, int cost){
        super(id, name, cost);
    }

    @Override
    public void setRent(int rent, int railroadCount){
        rent = 25 * railroadCount;
        super.setRent(rent, railroadCount);
    }

}
