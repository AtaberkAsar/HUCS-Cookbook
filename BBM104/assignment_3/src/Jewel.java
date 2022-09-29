import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public abstract class Jewel {
    private int point;
    private List<Integer[][]> checks = new ArrayList<>();

    public void popJewel(ArrayList<ArrayList<Jewel>> gameMap, int y, int x) throws NullJewelException{
        int[] mapSize = Bejeweled.getMapSize();
        checks = ((JewelSubclass) this).getChecks();

        for (Integer[][] jewelsToCheck : checks) {
            // get two adjacent jewels' coordinates
            int y1 = y + jewelsToCheck[0][0], x1 = x + jewelsToCheck[0][1],
                y2 = y + jewelsToCheck[1][0], x2 = x + jewelsToCheck[1][1];

            // if one of adjacent jewel's coordinates are out of map, continue
            if (y1 < 0 || y2 < 0 || x1 < 0 || x2 < 0 ||
                y1 >= mapSize[0] || y2 >= mapSize[0] || x1 >= mapSize[1] || x2 >= mapSize[1])
                continue;

            // if adjacent jewels are same as selected jewel's class
            if (this.getClass().isAssignableFrom((gameMap.get(y1).get(x1)).getClass()) &&
                this.getClass().isAssignableFrom((gameMap.get(y2).get(x2)).getClass())) {

                int score = this.getPoint() + gameMap.get(y1).get(x1).getPoint() + gameMap.get(y2).get(x2).getPoint();
                Bejeweled.setScore(score);

                gameMap.get(y).set(x, new NullJewel());
                gameMap.get(y1).set(x1, new NullJewel());
                gameMap.get(y2).set(x2, new NullJewel());
                break;
            }
        }
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getPoint(){
        return point;
    }
}

// JewelComparator will be used to update gameMap after making a move
class JewelComparator implements Comparator<Jewel>{
    @Override
    public int compare(Jewel jewel1, Jewel jewel2) {
        if(jewel1 instanceof NullJewel)
            return -1;
        return 0;
    }
}
