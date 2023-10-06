package Sorter;

public class SelectionSorter implements Sorter {

    @Override
    public void sort(int[] space) {
        for (int i = 0; i < space.length; i++) {
            int min = i;
            for (int j = i + 1; j < space.length; j++) {
                if (Sorter.lt(space[j], space[min]))
                    min = j;
            }
            Sorter.swap(space, i, min);
        }
    }

}
