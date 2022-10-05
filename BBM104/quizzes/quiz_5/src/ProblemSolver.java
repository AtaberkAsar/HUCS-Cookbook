import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class ProblemSolver {
    private static Queue<Integer> integerQueue = new Queue<>();

    public static void solveProblems(String inputFile){
        File inputs = new File(inputFile);
        File outputFile = new File("output.txt");
        Scanner inputScanner;
        PrintStream outputPrintStream;

        try {
            inputScanner = new Scanner(inputs);
            outputPrintStream = new PrintStream(outputFile);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            System.err.printf("\n'%s' or 'output.txt' files not found", inputFile);
            return;
        }
        while(inputScanner.hasNextLine()){
            String[] inputLine = inputScanner.nextLine().trim().split(" ");
            if(inputLine.length == 2)
                outputPrintStream.println(solveProblem2(inputLine[0], Integer.parseInt(inputLine[1])));
            else if (inputLine[0].startsWith("deque"))
                outputPrintStream.println(solveProblem2(inputLine[0], null));
            else
                outputPrintStream.println(solveProblem1(Integer.parseInt(inputLine[0])));
        }
        inputScanner.close();
        outputPrintStream.flush();
        outputPrintStream.close();
        delLastLine();
    }

    public static String solveProblem1(Integer num) {
        DecToOctWithStack decNumToOct = new DecToOctWithStack(num);
        return decNumToOct.getOctData();
    }

    public static String solveProblem2(String func, Integer num){
        switch (func) {
            case "enqueFront":
                integerQueue.enqueueFront(num);
                break;
            case "enqueMiddle":
                integerQueue.enqueueMiddle(num);
                break;
            case "enqueBack":
                integerQueue.enqueueBack(num);
                break;
            case "dequeFront":
                integerQueue.dequeueFront();
                break;
            case "dequeMiddle":
                integerQueue.dequeueMiddle();
                break;
            case "dequeBack":
                integerQueue.dequeueBack();
                break;
        }
        return integerQueue.toString();
    }

    public static void delLastLine(){
        File outputFile = new File("output.txt");
        Scanner inputScanner;
        StringBuilder file = new StringBuilder("");
        PrintStream outputStream;

        try {
            inputScanner = new Scanner(outputFile);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            System.err.print("\n'output.txt' file not found");
            return;
        }
        while(inputScanner.hasNextLine())
            file.append(inputScanner.nextLine()).append("\n");
        inputScanner.close();

        try {
            outputStream = new PrintStream(outputFile);
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
            System.err.print("\n'output.txt' file not found");
            return;
        }
        outputStream.print(file.deleteCharAt(file.length() - 1));
        outputStream.flush();
        outputStream.close();
    }
}
