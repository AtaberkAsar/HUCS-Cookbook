public class DecToOctWithStack {
    private final int decData;
    private String octData;
    private Stack<Integer> octStack;

    public DecToOctWithStack(int decData){
        this.decData = decData;
        octData = "";
        octStack = new Stack<>();
        try {
            DecToOctConverter();
        } catch (StackIsFullException stackIsFullException) {
            stackIsFullException.printStackTrace();
            System.err.printf("Stack overflow while converting decimal '%d' to octal\n", decData);
        }
    }

    public void DecToOctConverter() throws StackIsFullException{
        int decNum = decData;
        do {
            octStack.push(decNum % 8);
            decNum /= 8;
        } while (decNum != 0); // do while used in case of decData = 0
        octStackToOctData();
    }

    public void octStackToOctData(){
        try {
            while(true){
                octData += octStack.pop();
            }
        } catch (StackIsEmptyException stackIsEmptyException){}
    }

    public int getDecData() {
        return decData;
    }

    public String getOctData() {
        return octData;
    }
}
