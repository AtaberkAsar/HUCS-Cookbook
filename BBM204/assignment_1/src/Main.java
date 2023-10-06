import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

class Main {

    public static void main(String[] args) {
        try {
            Map<String, List<Double>> sortResults = Experimenter.sortExperiment(args[0]);
            Map<String, List<Double>> searchResults = Experimenter.searchExperiment(args[0]);

            String[] sortRandom = {"selectionSort - random", "quickSort - random", "bucketSort - random"};
            String[] sortAsc = {"selectionSort - asc", "quickSort - asc", "bucketSort - asc"};
            String[] sortDesc = {"selectionSort - desc", "quickSort - desc", "bucketSort - desc"};
            String[] search = {"linear - random", "linear - asc", "binary - asc"};

            Drawer.draw("Tests on Random Data", sortResults, sortRandom, "Milliseconds");
            Drawer.draw("Tests on Sorted Data (Ascending)", sortResults, sortAsc, "Milliseconds");
            Drawer.draw("Tests on Sorted Data (Descending)", sortResults, sortDesc, "Milliseconds");
            Drawer.draw("Tests on Search", searchResults, search, "Nanoseconds");

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }
}
