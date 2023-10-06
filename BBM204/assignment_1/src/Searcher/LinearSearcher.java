package Searcher;

public class LinearSearcher implements Searcher{

    @Override
    public int search(int[] space, int key) {
        for (int i = 0; i < space.length; ++i)
            if (space[i] == key)
                return i;
        return -1;
    }
}
