package Searcher;

public class BinarySearcher implements Searcher{

    @Override
    public int search(int[] space, int key) {
        int lo = 0;
        int hi = space.length - 1;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (space[mid] == key)
                return mid;

            if (space[mid] > key)
                hi = mid - 1;
            else
                lo = mid + 1;
        }

        return -1;
    }
}
