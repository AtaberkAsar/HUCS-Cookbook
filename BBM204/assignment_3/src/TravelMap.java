import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class TravelMap {

    // Maps a single Id to a single Location.
    public Map<Integer, Location> locationMap = new HashMap<>();

    // List of locations, read in the given order
    public List<Location> locations = new ArrayList<>();

    // List of trails, read in the given order
    public List<Trail> trails = new ArrayList<>();

    // TODO: You are free to add more variables if necessary.

    public Map<String, Trail> trailMap = new HashMap<>();

    public void initializeMap(String filename) {
        // Read the XML file and fill the instance variables locationMap, locations and trails.
        // TODO: Your code here
        try {
            File file = new File(filename);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            document.getDocumentElement().normalize();

            NodeList locationList = document.getElementsByTagName("Location");
            for(int temp = 0; temp < locationList.getLength(); ++temp) {
                Node node = locationList.item(temp);
                Element element = (Element) node;
                int id = Integer.parseInt(element.getElementsByTagName("Id").item(0).getTextContent());
                String name = element.getElementsByTagName("Name").item(0).getTextContent();
                Location location = new Location(name, id);
                locationMap.put(id, location);
                locations.add(location);
            }

            NodeList roadList = document.getElementsByTagName("Trail");

            for(int temp = 0; temp < roadList.getLength(); ++temp) {
                Node node = roadList.item(temp);
                Element element = (Element) node;
                int source = Integer.parseInt(element.getElementsByTagName("Source").item(0).getTextContent());
                int destination = Integer.parseInt(element.getElementsByTagName("Destination").item(0).getTextContent());
                int danger = Integer.parseInt(element.getElementsByTagName("Danger").item(0).getTextContent());
                Location sourceLocation = locationMap.get(source);
                Location destinationLocation = locationMap.get(destination);
                Trail trail = new Trail(sourceLocation, destinationLocation, danger);
                trails.add(trail);

                String key = source + " " + destination;
                trailMap.put(key, trail);
                key = destination + " " + source;
                trailMap.put(key, trail);

            }

        } catch (ParserConfigurationException | SAXException | IOException ignored) {

        }

    }

    public List<Trail> getSafestTrails() {
        List<Trail> safestTrails = new ArrayList<>();
        // Fill the safestTrail list and return it.
        // Select the optimal Trails from the Trail list that you have read.
        // TODO: Your code here

        // Prim's Algorithm Lazy
        Queue<Trail> pq = new PriorityQueue<>();
        boolean[] marked = new boolean[locations.size()];
        visit(locations.get(0).id, marked, pq);
        while (!pq.isEmpty()) {
            Trail t = pq.poll();
            int s = t.source.id;
            int d = t.destination.id;
            if (marked[s] && marked[d])
                continue;
            safestTrails.add(t);
            if(!marked[s])
                visit(s, marked, pq);
            if (!marked[d])
                visit(d, marked, pq);
        }

        return safestTrails;
    }

    public void printSafestTrails(List<Trail> safestTrails) {
        // Print the given list of safest trails conforming to the given output format.
        // TODO: Your code here

        int dangerSum = 0;
        System.out.println("Safest trails are:");
        for(Trail t : safestTrails) {
            System.out.println("The trail from " + t.source.name + " to " + t.destination.name + " with danger " + t.danger);
            dangerSum += t.danger;
        }
        System.out.println("Total danger: " + dangerSum);
    }

    private void visit(int v, boolean[] marked, Queue<Trail> pq) {
        marked[v] = true;
        for(Trail t: trails) {
            if (t.source.id == v || t.destination.id == v)
                if (!marked[t.source.id] || !marked[t.destination.id])
                    pq.add(trailMap.get(t.source.id + " " + t.destination.id));
        }
    }
}
