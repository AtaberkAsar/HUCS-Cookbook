package Sorter;

public class QuickSorter implements Sorter{

    @Override
    public void sort(int[] space) {
        quickSort(space, 0, space.length - 1);
    }

    private static void quickSort(int[] space, int lo, int hi) {
        int stackSize = hi - lo + 1;
        int[] stack = new int[stackSize];
        int top = -1;

        stack[++top] = lo;
        stack[++top] = hi;

        while (top >= 0) {
            hi = stack[top--];
            lo = stack[top--];
            int pivot = partition(space, lo, hi);

            if (pivot -1 > lo) {
                stack[++top] = lo;
                stack[++top] = pivot - 1;
            }
            if (pivot + 1 < hi) {
                stack[++top] = pivot + 1;
                stack[++top] = hi;
            }
        }

    }

    private static int partition(int[] space, int lo, int hi) {
        int pivot = space[hi];
        int i = lo - 1;

        for(int j = lo; j < hi; ++j)
            if (space[j] <= pivot)
                Sorter.swap(space, ++i, j);
        Sorter.swap(space, ++i, hi);
        return i;
    }

}
