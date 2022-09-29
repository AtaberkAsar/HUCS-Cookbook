import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pipe extends MathSymbol {
    private static List<Integer[][]> checks = new ArrayList<>();

    public Pipe(){
        try{
            checks.add(new Integer[][]{new Integer[]{1, 0}, new Integer[]{2, 0}}); // 2
            checks.add(new Integer[][]{new Integer[]{-1, 0}, new Integer[]{-2, 0}}); // 8
            checks = Collections.unmodifiableList(checks); // make sure that checks defined at the start of the game, and not edited
        }catch(UnsupportedOperationException unsupportedOperationException){
            // this error means this is not the first pipe created in this game, thus no need to edit checks
        }
    }

    @Override
    public String toString() {
        return "|";
    }

    public List<Integer[][]> getChecks() {
        return checks;
    }
}
