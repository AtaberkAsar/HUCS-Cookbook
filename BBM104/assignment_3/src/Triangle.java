import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Triangle extends Symbol{
    private static List<Integer[][]> checks = new ArrayList<>();

    public Triangle(){
        try{
            checks.add(new Integer[][]{new Integer[]{-1, 0}, new Integer[]{-2, 0}}); // 8
            checks.add(new Integer[][]{new Integer[]{1, 0}, new Integer[]{2, 0}}); // 2
            checks = Collections.unmodifiableList(checks); // make sure that checks defined at the start of the game, and not edited
        }catch(UnsupportedOperationException unsupportedOperationException){
            // this error means this is not the first triangle created in this game, thus no need to edit checks
        }
        setPoint(15);
    }

    @Override
    public String toString() {
        return "T";
    }

    public List<Integer[][]> getChecks() {
        return checks;
    }
}
