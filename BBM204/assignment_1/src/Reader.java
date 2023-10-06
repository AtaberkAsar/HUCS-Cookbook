import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {

    public static int[] read (String filename, int n) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        scanner.nextLine();

        int[] arr = new int[n];
        for (int i = 0; i < n; ++i)
            arr[i] = Integer.parseInt(scanner.nextLine().trim().split(",")[6]);

        scanner.close();
        return arr;
    }

}
