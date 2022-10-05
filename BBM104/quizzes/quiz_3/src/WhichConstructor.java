public class WhichConstructor {
    WhichConstructor(String s) {
        this(2);
        System.out.println("in string constructor...");
    }

    WhichConstructor(int x) {
        this();
        System.out.println("in int constructor...");
    }

    WhichConstructor() {
        System.out.println("in empty / default constructor...");
    }

    public static void main(String[] args) {
        WhichConstructor wc = new WhichConstructor("test");
    }
}
