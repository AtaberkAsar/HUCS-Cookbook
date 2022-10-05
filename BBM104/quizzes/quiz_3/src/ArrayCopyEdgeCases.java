import java.util.ArrayList;
import java.util.Arrays;

public class ArrayCopyEdgeCases {
    public static void main(String[] args) {
        int[] intArray = new int[10];
        for(int i = 0; i < 10; i++){
            intArray[i] = i;
        }
        System.arraycopy(intArray, 1, intArray, 3, 3);
        System.out.println(Arrays.toString(intArray));
        System.out.println(intArray.length);

        System.arraycopy(intArray, 1, intArray, 8, 3); // Gives RTE
        System.out.println(Arrays.toString(intArray));
        System.out.println(intArray.length);

        System.arraycopy(intArray, 1, intArray, 3, 8); // Gives RTE
        System.out.println(Arrays.toString(intArray));
        System.out.println(intArray.length);
    }
}
