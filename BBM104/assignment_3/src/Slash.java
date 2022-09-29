import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Slash extends MathSymbol {
    private static List<Integer[][]> checks = new ArrayList<>();

    public Slash(){
        try{
            checks.add(new Integer[][]{new Integer[]{1, -1}, new Integer[]{2, -2}}); // 1
            checks.add(new Integer[][]{new Integer[]{-1, 1}, new Integer[]{-2, 2}}); // 9
            checks = Collections.unmodifiableList(checks); // make sure that checks defined at the start of the game, and not edited
        }catch(UnsupportedOperationException unsupportedOperationException){
            // this error means this is not the first slash created in this game, thus no need to edit checks
        }
    }

    @Override
    public String toString() {
        return "/";
    }

    public List<Integer[][]> getChecks() {
        return checks;
    }
}
