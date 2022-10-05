import java.util.ArrayList;
import java.util.List;

public class GenericTest {

    public static <T> void testFuncArr(List<T> tester1, List<T> tester2){
        System.out.println(tester1);
        System.out.println(tester2);
    }

    public static <T> void testFunc(T tester1, T tester2){
        System.out.println(tester1);
        System.out.println(tester2);
    }

    public static void main(String[] args){
        List<Integer> test = new ArrayList<>();
        List<String> test2 = new ArrayList<>();

        Integer myTest = 10;
        String myNewTest = "12345";

        testFunc(myTest, myNewTest);

        //testFuncArr(test, test2);
    }

}
