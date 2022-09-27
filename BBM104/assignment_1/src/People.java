import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class People {
    private final String personID;
    private String name;
    private boolean gender; // Female: 0, Male: 1
    private int weight;
    private int height;
    private final int dateOfBirth;
    private int age;
    private int dailyCalorieNeed;
    private int dailyCalorieTaken;
    private int dailyCalorieBurned;

    /**
     * @param personID ID of the person
     * @param name name of the person
     * @param gender gender of the person
     * @param weight weight of the person, in kg
     * @param height height of the person, in cm
     * @param dateOfBirth dateOfBirth of person, in years
     */
    public People(String personID, String name, String gender, int weight, int height, int dateOfBirth){
        this.personID = personID;
        this.name = name;
        this.gender = gender.equals("male");
        this.weight = weight;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        this.age = 2022 - dateOfBirth;
        this.getDailyCalorieNeed();
    }

    /**
     * reads given people file
     * @param path path of the people file
     * @param people People [], that information from the file will be written into
     */
    public static void readPeople(String path ,People [] people){

        File peopleFile = new File(path);
        Scanner peopleScanner;
        try {
            peopleScanner = new Scanner(peopleFile);
        } catch (IOException ioException) {
            return;
        }

        for(int i = 0; peopleScanner.hasNextLine(); i++){
            String [] personInfo = peopleScanner.nextLine().split("\t");
            people[i] = new People(personInfo[0], personInfo[1], personInfo[2], Integer.parseInt(personInfo[3]),
                    Integer.parseInt(personInfo[4]), Integer.parseInt(personInfo[5]));
        }
        peopleScanner.close();
    }

    /**
     * Calculates daily calorie need, based on gender
     */
    public int getDailyCalorieNeed(){
        if (gender){ // if male
            dailyCalorieNeed = (int) Math.round(66 + 13.75 * weight + 5 * height - 6.8 * (age));
        }
        else{ // if female
            dailyCalorieNeed = (int) Math.round(665 + 9.6 * weight + 1.7 * height - 4.7 * (age));
        }
        return dailyCalorieNeed;
    }

    /**
     * updates daily calories taken
     * @param calories amount of calories taken
     */
    public void calorieIntake(int calories){
        dailyCalorieTaken += calories;
    }

    /**
     * updates daily calories burned
     * @param calories amount of calories burned
     */
    public void calorieBurn(int calories){
        dailyCalorieBurned += calories;
    }

    public int getResult(){
        return dailyCalorieTaken - dailyCalorieNeed - dailyCalorieBurned;
    }

    public String getPersonID(){
        return personID;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender ? "male" : "female";
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public int getDailyCalorieTaken() {
        return dailyCalorieTaken;
    }

    public int getDailyCalorieBurned() {
        return dailyCalorieBurned;
    }

}
