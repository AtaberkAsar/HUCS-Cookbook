import java.io.PrintStream;
import java.util.Scanner;

public class Monitor {
    static String line;

    /**
     * updates the calories taken by the person
     *
     * @param personID        ID of the person
     * @param foodID          ID of the food
     * @param numberOfPortion number of portion of the food
     * @param people          people list
     * @param foods           food list
     */
    public static void hasTakenCalories(String personID, int foodID, int numberOfPortion, People[] people, Food[] foods) {
        for (People person : people) {
            if(person == null){
                break;
            }

            if (person.getPersonID().equals(personID)) {
                for (Food food : foods) {
                    if (food.getFoodID() == foodID) {
                        int calories = food.getCalorieCount() * numberOfPortion;
                        person.calorieIntake(calories);
                        line = String.format("%s\thas\ttaken\t%dkcal\tfrom\t%s", person.getPersonID(), calories, food.getNameOfFood());
                        return;
                    }
                }
            }
        }
    }

    /**
     * updates the calories burned by the person
     *
     * @param personID      ID of the person
     * @param sportID       ID of the food
     * @param sportDuration duration of the sport
     * @param people        people list
     * @param sports        sport list
     */
    public static void hasBurnedCalories(String personID, int sportID, int sportDuration, People[] people, Sport[] sports) {
        for (People person : people) {
            if (person == null) {
                break;
            }

            if (person.getPersonID().equals(personID)) {
                for (Sport sport : sports) {
                    if (sport.getSportID() == sportID) {
                        int calories = sport.getCalorieBurned() * sportDuration / 60;
                        person.calorieBurn(calories);
                        line = String.format("%s\thas\tburned\t%dkcal\tthanks\tto\t%s", person.getPersonID(),
                                calories, sport.getNameOfSport());
                        return;
                    }
                }
            }
        }
    }

    /**
     * get info about given personID, name, age, dailyCalorieNeed, calorieTaken, calorieBurned, result
     *
     * @param personID ID of the person
     * @param people   people list
     */
    public static void printPersonID(String personID, People[] people) {
        for (People person : people) {
            if (person == null) {
                break;
            }

            if (person.getPersonID().equals(personID)) {
                line = String.format("%s\t%d\t%dkcal\t%dkcal\t%dkcal\t%c%dkcal", person.getName(), person.getAge(),
                        person.getDailyCalorieNeed(), person.getDailyCalorieTaken(),
                        person.getDailyCalorieBurned(), person.getResult() > 0 ? '+' : '-', Math.abs(person.getResult()));
                return;
            }
        }
    }

    /**
     * get people that takes more than their daily calorie needs
     * @param people people list
     */
    public static void printWarn(People[] people) {
        boolean flag = false;
        line = "";
        for (People person : people) {
            if (person == null) {
                break;
            }

            if (person.getResult() > 0) {
                flag = true;
                line += String.format("%s\t%d\t%dkcal\t%dkcal\t%dkcal\t+%dkcal\n", person.getName(), person.getAge(),
                        person.getDailyCalorieNeed(), person.getDailyCalorieTaken(),
                        person.getDailyCalorieBurned(), person.getResult());
            }
        }
        if (!flag) {
            line = "There\tis\tno\tsuch\tperson\n"; // If no person needs to be warned
        }
    }

    /**
     * get active people list
     * @param people people list
     */
    public static void printList(People[] people) {
        line = "";
        for (People person : people) {
            if (person == null) {
                break;
            }

            if (person.getDailyCalorieTaken() == 0 && person.getDailyCalorieBurned() == 0) {
                continue;
            }

            line += String.format("%s\t%d\t%dkcal\t%dkcal\t%dkcal\t%c%dkcal\n", person.getName(), person.getAge(),
                    person.getDailyCalorieNeed(), person.getDailyCalorieTaken(),
                    person.getDailyCalorieBurned(), person.getResult() > 0 ? '+' : '-', Math.abs(person.getResult()));
        }
    }

    /**
     * read commands from command file, and write to monitoring file
     * @param commandScanner Scanner command.txt file
     * @param monitorWriter PrintStream monitoring.txt file
     * @param people people list
     * @param foods foods list
     * @param sports sports list
     */
    public static void monitoringWriter(Scanner commandScanner, PrintStream monitorWriter, People[] people, Food[] foods, Sport[] sports){
        while (commandScanner.hasNextLine()){
            String[] operation = commandScanner.nextLine().split("\t");
            if (operation.length == 3) {
                if (operation[1].charAt(0) == '1') { // foodID starts with 1
                    Monitor.hasTakenCalories(operation[0], Integer.parseInt(operation[1]), Integer.parseInt(operation[2]), people, foods);
                    monitorWriter.println(Monitor.line);
                }
                else { // sportID starts with 2
                    Monitor.hasBurnedCalories(operation[0], Integer.parseInt(operation[1]), Integer.parseInt(operation[2]), people, sports);
                    monitorWriter.println(Monitor.line);
                }
            }
            else {
                switch(operation[0].charAt(5)){
                    case 'W': // printWarn
                        Monitor.printWarn(people);
                        monitorWriter.print(Monitor.line);
                        break;
                    case 'L': // printList
                        Monitor.printList(people);
                        monitorWriter.print(Monitor.line);
                        break;
                    default: // print(PersonID)
                        Monitor.printPersonID(operation[0].substring(6, operation[0].length() - 1), people);
                        monitorWriter.println(Monitor.line);
                        break;
                }
            }
            for (int i = 0; i < 15; i++){ // writing 15 stars
                monitorWriter.print('*');
            }
            monitorWriter.println();
        }
    }

    /**
     * delete last two lines (last 15 stars and 2 newline)
     * @param monitoringScanner Scanner monitoring.txt
     * @return String file without last two lines
     */
    public static String delLastLines(Scanner monitoringScanner){
        String file = "";
        while(monitoringScanner.hasNextLine()){
            file += monitoringScanner.nextLine() + "\n";
        }
        return file.substring(0, file.length() -17); // 15 stars + 2 newlines = 17
    }
}
