package Sorter;

public interface Sorter {

    abstract void sort(int[] space);

    static <T extends Comparable<T>> boolean lt(T item0, T item1) {
        return item0.compareTo(item1) < 0;
    }

    static void swap(int[] space, int i, int j) {
        int val = space[i];
        space[i] = space[j];
        space[j] = val;
    }

}
