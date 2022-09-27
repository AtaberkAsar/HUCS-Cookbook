import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        People [] people = new People[100]; // people list
        Food [] foods = new Food[100]; // foods list
        Sport [] sports = new Sport[100]; // sports list
        String file; // String file, will be used to remove last 2 lines from output file

        People.readPeople("people.txt", people);
        Food.readFoods("food.txt", foods);
        Sport.readSports("sport.txt", sports);

        File commandFile = new File(args[0]);
        File monitorFile = new File("monitoring.txt");

        Scanner commandScanner;
        PrintStream monitorWriter;
        Scanner monitoringScanner;
        try {
            commandScanner = new Scanner(commandFile);
            monitorWriter = new PrintStream(monitorFile);
        } catch (IOException ioException) {
            return;
        }

        Monitor.monitoringWriter(commandScanner, monitorWriter, people, foods, sports); // read commands and write to output file
        commandScanner.close();
        monitorWriter.close();

        try {
            monitoringScanner = new Scanner(monitorFile);
        } catch (IOException ioException) {
            return;
        }

        file = Monitor.delLastLines(monitoringScanner); // removing last two lines to match the output format
        monitoringScanner.close();

        try {
            monitorWriter = new PrintStream(monitorFile);
        } catch (IOException ioException) {
            return;
        }

        monitorWriter.print(file); // printing the file (without last 2 lines)
        monitorWriter.close();
    }
}
