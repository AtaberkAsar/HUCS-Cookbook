public class StringClass {
    public static void main(String[] args) {
        String s = "severe";
        int n = 0;
        for (int i = 0; i < s.length(); i = i+1)
            if (s.charAt(i) == 'e')
                n = n * 2;
            else
                n = n * 2 + 1;
        System.out.println(n);
        System.out.println(s.concat("-class"));
        if(s.equals("severe-class"))
            System.out.println("Two strings are equal.");
        else
            System.out.println("Two strings are not equal.");
    }
}
