import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Diamond extends Symbol {
    private static List<Integer[][]> checks = new ArrayList<>();

    public Diamond(){
        try{
            checks.add(new Integer[][]{new Integer[]{1, -1}, new Integer[]{2, -2}}); // 1
            checks.add(new Integer[][]{new Integer[]{-1, 1}, new Integer[]{-2, 2}}); // 9
            checks.add(new Integer[][]{new Integer[]{1, 1}, new Integer[]{2, 2}}); // 3
            checks.add(new Integer[][]{new Integer[]{-1, -1}, new Integer[]{-2, -2}}); // 7
            checks = Collections.unmodifiableList(checks); // make sure that checks defined at the start of the game, and not edited
        }catch(UnsupportedOperationException unsupportedOperationException){
            // this error means this is not the first diamond created in this game, thus no need to edit checks
        }
        setPoint(30);
    }

    @Override
    public String toString() {
        return "D";
    }

    public List<Integer[][]> getChecks() {
        return checks;
    }
}
