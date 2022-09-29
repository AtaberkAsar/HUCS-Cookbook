import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Wildcard extends Symbol{
    private static List<Integer[][]> checks = new ArrayList<>();

    public Wildcard(){
        try{
            checks.add(new Integer[][]{new Integer[]{1, 0}, new Integer[]{2, 0}}); // 2
            checks.add(new Integer[][]{new Integer[]{-1, 0}, new Integer[]{-2, 0}}); // 8
            checks.add(new Integer[][]{new Integer[]{0, -1}, new Integer[]{0, -2}}); // 4
            checks.add(new Integer[][]{new Integer[]{0, 1}, new Integer[]{0, 2}}); // 6
            checks.add(new Integer[][]{new Integer[]{1, -1}, new Integer[]{2, -2}}); // 1
            checks.add(new Integer[][]{new Integer[]{-1, 1}, new Integer[]{-2, 2}}); // 9
            checks.add(new Integer[][]{new Integer[]{1, 1}, new Integer[]{2, 2}}); // 3
            checks.add(new Integer[][]{new Integer[]{-1, -1}, new Integer[]{-2, -2}}); // 7
            checks = Collections.unmodifiableList(checks); // make sure that checks defined at the start of the game, and not edited
        }catch(UnsupportedOperationException unsupportedOperationException){
            // this error means this is not the first wildcard created in this game, thus no need to edit checks
        }

        setPoint(10);
    }

    @Override
    public void popJewel(ArrayList<ArrayList<Jewel>> gameMap, int y, int x) throws NullJewelException{
        int[] mapSize = Bejeweled.getMapSize();

        for (Integer[][] jewelsToCheck : checks) {
            int y1 = y + jewelsToCheck[0][0], x1 = x + jewelsToCheck[0][1],
                y2 = y + jewelsToCheck[1][0], x2 = x + jewelsToCheck[1][1];

            if (y1 < 0 || y2 < 0 || x1 < 0 || x2 < 0 ||
                y1 >= mapSize[0] || y2 >= mapSize[0] || x1 >= mapSize[1] || x2 >= mapSize[1])
                continue;

            // if adjacent jewels are instanceof Symbol and (
            //    adjacent jewels are instanceof same jewel or
            //    one of the adjacent jewels is Wildcard    )
            if ((gameMap.get(y1).get(x1) instanceof Symbol && gameMap.get(y2).get(x2) instanceof Symbol &&
                (gameMap.get(y1).get(x1).getClass().isAssignableFrom((gameMap.get(y2).get(x2)).getClass()) ||
                 gameMap.get(y1).get(x1) instanceof Wildcard || gameMap.get(y2).get(x2) instanceof Wildcard))
                ){
                int score = this.getPoint() + gameMap.get(y1).get(x1).getPoint() + gameMap.get(y2).get(x2).getPoint();
                Bejeweled.setScore(score);

                gameMap.get(y).set(x, new NullJewel());
                gameMap.get(y1).set(x1, new NullJewel());
                gameMap.get(y2).set(x2, new NullJewel());
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "W";
    }

    public List<Integer[][]> getChecks() {
        return checks;
    }
}
