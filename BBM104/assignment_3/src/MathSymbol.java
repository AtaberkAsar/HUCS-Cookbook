import java.util.ArrayList;
import java.util.List;

public abstract class MathSymbol extends Jewel implements JewelSubclass{
    private List<Integer[][]> checks = new ArrayList<>();

    MathSymbol(){
        setPoint(20);
    }

    @Override
    public void popJewel(ArrayList<ArrayList<Jewel>> gameMap, int y, int x) throws NullJewelException{
        int[] mapSize = Bejeweled.getMapSize();

        checks = this.getChecks();

        for (Integer[][] jewelsToCheck : checks) {
            int y1 = y + jewelsToCheck[0][0], x1 = x + jewelsToCheck[0][1],
                y2 = y + jewelsToCheck[1][0], x2 = x + jewelsToCheck[1][1];

            if (y1 < 0 || y2 < 0 || x1 < 0 || x2 < 0 ||
                y1 >= mapSize[0] || y2 >= mapSize[0] || x1 >= mapSize[1] || x2 >= mapSize[1])
                continue;

            if (gameMap.get(y1).get(x1) instanceof MathSymbol && gameMap.get(y2).get(x2) instanceof MathSymbol) {

                int score = this.getPoint() + gameMap.get(y1).get(x1).getPoint() + gameMap.get(y2).get(x2).getPoint();
                Bejeweled.setScore(score);

                gameMap.get(y).set(x, new NullJewel());
                gameMap.get(y1).set(x1, new NullJewel());
                gameMap.get(y2).set(x2, new NullJewel());
                break;
            }
        }
    }
}
