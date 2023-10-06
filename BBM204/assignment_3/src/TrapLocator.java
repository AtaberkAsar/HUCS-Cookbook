import java.util.*;

public class TrapLocator {
    public List<Colony> colonies;

    public TrapLocator(List<Colony> colonies) {
        this.colonies = colonies;
    }

    public List<List<Integer>> revealTraps() {

        // Trap positions for each colony, should contain an empty array if the colony is safe.
        // I.e.:
        // 0 -> [2, 15, 16, 31]
        // 1 -> [4, 13, 22]
        // 3 -> []
        // ...
        List<List<Integer>> traps = new ArrayList<>();

        // Identify the time traps and save them into the traps variable and then return it.
        // TODO: Your code here

        int cityCount = 0;
        for(Colony colony : colonies)
            cityCount += colony.cities.size();

        for(int col = 0; col < colonies.size(); ++col) {
            Colony colony = colonies.get(col);
            traps.add(new ArrayList<>());

            boolean[] marked = new boolean[cityCount + 1];
            for(int city : colony.cities) {
                int loop = depthFirstCycleDetect(colony, city, marked);

                // if there is loop node
                if (loop != -1) {
                    resetMarked(marked);

                    // mark only loop nodes
                    depthFirstCycleDetect(colony, loop, marked);

                    // add them to traps
                    for (int i = 1; i < marked.length; ++i)
                        if (marked[i])
                            traps.get(col).add(i);
                    break;
                }
            }
        }

        return traps;
    }

    private int depthFirstCycleDetect(Colony colony, int s, boolean[] marked) {
        if (marked[s])
            return s;

        marked[s] = true;
        for (int d : colony.roadNetwork.get(s)) {
            int node = depthFirstCycleDetect(colony, d, marked);
            if (marked[d] && node != -1)
                return node;
        }

        marked[s] = false;

        return -1;
    }

    private void resetMarked(boolean[] marked) {
        Arrays.fill(marked, false);
    }

    public void printTraps(List<List<Integer>> traps) {
        // For each colony, if you have encountered a time trap, then print the cities that create the trap.
        // If you have not encountered a time trap in this colony, then print "Safe".
        // Print the your findings conforming to the given output format.
        // TODO: Your code here

        System.out.println("Danger exploration conclusions:");
        int colonyCounter = 1;
        for(List<Integer> trap : traps) {
            if (trap.size() == 0)
                System.out.println("Colony " + colonyCounter + ": Safe");
            else
                System.out.println("Colony " + colonyCounter + ": Dangerous. Cities on the dangerous path: " + trap);
            colonyCounter++;
        }
    }

}
