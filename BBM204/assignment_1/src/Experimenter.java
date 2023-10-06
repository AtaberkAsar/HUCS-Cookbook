import Searcher.*;
import Sorter.*;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Experimenter {

    public static Map<String, List<Double>> sortExperiment(String file) throws FileNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        double experimentNum = 10.0;
        int[] sizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};

        String[] sortMethods = {"selectionSort", "quickSort", "bucketSort"};
        Map<String, Sorter> sorters = new HashMap<String, Sorter>();
        sorters.put("selectionSort", new SelectionSorter());
        sorters.put("quickSort", new QuickSorter());
        sorters.put("bucketSort", new BucketSorter());

        Map<String, List<Double>> results = new HashMap<>();

        for (String method : sortMethods) {
            System.out.println(method);

            List<Double> result;

            // Random Input
            System.out.println("Random Input");
            result = new ArrayList<>();
            for (int s = 0; s < 10; ++s) {
                int sum = 0;

                for (int i = 0; i < experimentNum; ++i) {
                    int[] arr = Reader.read(file, sizes[s]);

                    long start = System.currentTimeMillis();
                    sorters.get(method).sort(arr);
                    long end = System.currentTimeMillis();

                    sum += end - start;
                }

                System.out.print(sum / experimentNum + "\t|\t");
                result.add(sum / experimentNum);
            }

            System.out.println();
            results.put(method + " - random", result);
            result = new ArrayList<>();

            // Ascending Input
            System.out.println("Ascending Input");
            for (int s = 0; s < 10; ++s) {
                int sum = 0;

                for (int i = 0; i < experimentNum; ++i) {
                    int[] arr = Reader.read(file, sizes[s]);
                    Arrays.sort(arr);

                    long start = System.currentTimeMillis();
                    sorters.get(method).sort(arr);
                    long end = System.currentTimeMillis();

                    sum += end - start;
                }

                System.out.print(sum / experimentNum + "\t|\t");
                result.add(sum / experimentNum);
            }

            System.out.println();
            results.put(method + " - asc", result);
            result = new ArrayList<>();

            // Descending Input
            System.out.println("Descending Input");
            for (int s = 0; s < 10; ++s) {
                int sum = 0;

                for (int i = 0; i < experimentNum; ++i) {
                    int[] arr = Reader.read(file, sizes[s]);
                    arr = Arrays.stream(arr).boxed()
                            .sorted(Collections.reverseOrder())
                            .mapToInt(Integer::intValue).toArray();

                    long start = System.currentTimeMillis();
                    sorters.get(method).sort(arr);
                    long end = System.currentTimeMillis();

                    sum += end - start;
                }

                System.out.print(sum / experimentNum + "\t|\t");
                result.add(sum / experimentNum);
            }

            System.out.println("\n");
            results.put(method + " - desc", result);
        }

        return results;
    }

    public static Map<String, List<Double>> searchExperiment(String file) throws FileNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        double experimentNum = 1000.0;
        int[] sizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};

        Map<String, List<Double>> results = new HashMap<>();

        // Random Input
        System.out.println("Random Input Linear Search");
        List<Double> result = new ArrayList<>();
        for (int s = 0; s < 10; ++s) {
            int sum = 0;

            for (int i = 0; i < experimentNum; ++i) {
                int[] arr = Reader.read(file, sizes[s]);

                int rand = ThreadLocalRandom.current().nextInt(sizes[s]);
                int key = arr[rand];

                long start = System.nanoTime();
                new LinearSearcher().search(arr, key);
                long end = System.nanoTime();

                sum += end - start;
            }

            System.out.print(sum / experimentNum + "\t|\t");
            result.add(sum / experimentNum);
        }

        System.out.println();
        results.put("linear - random", result);

        // Sorted Input
        System.out.println("Sorted Input Linear Search");
        result = new ArrayList<>();
        for (int s = 0; s < 10; ++s) {
            int sum = 0;

            for (int i = 0; i < experimentNum; ++i) {
                int[] arr = Reader.read(file, sizes[s]);
                Arrays.sort(arr);

                int rand = ThreadLocalRandom.current().nextInt(sizes[s]);
                int key = arr[rand];

                long start = System.nanoTime();
                new LinearSearcher().search(arr, key);
                long end = System.nanoTime();

                sum += end - start;
            }

            System.out.print(sum / experimentNum + "\t|\t");
            result.add(sum / experimentNum);
        }

        System.out.println();
        results.put("linear - asc", result);

        // Sorted Input
        System.out.println("Sorted Input Binary Search");
        result = new ArrayList<>();
        for (int s = 0; s < 10; ++s) {
            int sum = 0;

            for (int i = 0; i < experimentNum; ++i) {
                int[] arr = Reader.read(file, sizes[s]);
                Arrays.sort(arr);

                int rand = ThreadLocalRandom.current().nextInt(sizes[s]);
                int key = arr[rand];

                long start = System.nanoTime();
                new BinarySearcher().search(arr, key);
                long end = System.nanoTime();

                sum += end - start;
            }

            System.out.print(sum / experimentNum + "\t|\t");
            result.add(sum / experimentNum);
        }

        System.out.println();
        results.put("binary - asc", result);

        return results;
    }

}


