import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Square extends Symbol {
    private static List<Integer[][]> checks = new ArrayList<>();

    public Square(){
        try{
            checks.add(new Integer[][]{new Integer[]{0, -1}, new Integer[]{0, -2}}); // 4
            checks.add(new Integer[][]{new Integer[]{0, 1}, new Integer[]{0, 2}}); // 6
            checks = Collections.unmodifiableList(checks); // make sure that checks defined at the start of the game, and not edited
        }catch(UnsupportedOperationException unsupportedOperationException){
            // this error means this is not the first square created in this game, thus no need to edit checks
        }
        setPoint(15);
    }

    @Override
    public String toString() {
        return "S";
    }

    public List<Integer[][]> getChecks() {
        return checks;
    }
}
