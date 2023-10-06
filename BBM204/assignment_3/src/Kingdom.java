import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Kingdom {

    // TODO: You should add appropriate instance variables.
    boolean[][] adj;

    public void initializeKingdom(String filename){
        // Read the txt file and fill your instance variables
        // TODO: Your code here

        File file = new File(filename);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);

            boolean flag = true;
            int sourceCounter = 0;
            while (scanner.hasNextLine()) {
                String[] line = scanner.nextLine().trim().split(" ");
                if (flag) {
                    adj = new boolean[line.length][line.length];
                    flag = false;
                }

                for (int destinationCounter = 0; destinationCounter < line.length; ++destinationCounter)
                    if (line[destinationCounter].equals("1"))
                        adj[sourceCounter][destinationCounter] = true;

                ++sourceCounter;
            }
        } catch (FileNotFoundException ignored) {

        }

    }

    public List<Colony> getColonies() {
        List<Colony> colonies = new ArrayList<>();
        // TODO: DON'T READ THE .TXT FILE HERE!
        // Identify the colonies using the given input file.
        // TODO: Your code here

        List<Integer>[] undirectedAdjList = new ArrayList[adj.length];
        for (int i = 0; i < adj.length; ++i)
            undirectedAdjList[i] = new ArrayList<>();
        for (int i = 0; i < adj.length; ++i)
            for (int j = 0; j < adj.length; ++j)
                if(adj[i][j]) {
                    undirectedAdjList[i].add(j);
                    undirectedAdjList[j].add(i);
                }

        boolean[] marked = new boolean[adj.length];
        int[] cc = new int[adj.length];
        int connection = 0;

        // Fill cc
        for (int i = 0; i < adj.length; ++i)
            if (!marked[i]) {
                depthFirst(undirectedAdjList, i, marked, cc, connection);
                ++connection;
            }

        // Fill colonies
        for (int i = 0; i < connection; ++i)
            colonies.add(new Colony());
        for (int s = 0; s < cc.length; ++s) {
            colonies.get(cc[s]).cities.add(s + 1);

            List<Integer> cities = new ArrayList<>();
            for (int d = 0; d < adj.length; ++d)
                if (adj[s][d])
                    cities.add(d + 1);

            colonies.get(cc[s]).roadNetwork.put(s + 1, cities);
        }

        return colonies;
    }

    public void printColonies(List<Colony> discoveredColonies) {
        // Print the given list of discovered colonies conforming to the given output format.
        // TODO: Your code here

        System.out.println("Discovered colonies are:");
        int colonyCounter = 1;
        for(Colony colony : discoveredColonies) {
            System.out.print("Colony " + colonyCounter + ": ");
            Collections.sort(colony.cities);
            System.out.println(colony.cities);
            ++colonyCounter;
        }
    }

    private void depthFirst(List<Integer>[] graph, int s, boolean[] marked, int[] cc, int connection) {
        marked[s] = true;
        cc[s] = connection;
        for (int d : graph[s])
            if (!marked[d])
                depthFirst(graph, d, marked, cc, connection);
    }

}
