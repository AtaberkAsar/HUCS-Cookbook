package Sorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BucketSorter implements Sorter{

    @SuppressWarnings("all")
    @Override
    public void sort(int[] space) {
        int bucketNum = (int) Math.ceil(Math.sqrt(space.length));
        List<Integer>[] buckets = new ArrayList[bucketNum];
        for (int i = 0; i < bucketNum; ++i)
            buckets[i] = new ArrayList<>();
        int max = max(space);

        for (int i : space)
            buckets[hash(i, max, bucketNum)].add(i);
        for (List<Integer> bucket : buckets)
            Collections.sort(bucket);

        int i = 0;
        for(List<Integer> bucket : buckets)
            for (int item : bucket)
                space[i++] = item;
    }

    private static int max(int[] space) {
        int max = Integer.MIN_VALUE;
        for (int i : space)
            if (i > max)
                max = i;

        return max;
    }

    private static int hash (int i, int max, int bucketNum) {
        return (int) (1.0 * i / max * (bucketNum - 1));
    }

}
