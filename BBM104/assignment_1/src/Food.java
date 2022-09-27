import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Food {
    private int foodID;
    private String nameOfFood;
    private int calorieCount; // for 1 portion, 1 portion = 100 g

    /**
     * @param foodID ID of the food
     * @param nameOfFood name of the food
     * @param calorieCount calorie count of the food, for 1 portion, 1 portion = 100 g
     */
    public Food(int foodID, String nameOfFood, int calorieCount){
        this.foodID = foodID;
        this.nameOfFood = nameOfFood;
        this.calorieCount = calorieCount;
    }

    /**
     * reads given food file
     * @param path path of the food file
     * @param foods Food [], that information from the file will be written into
     */
    public static void readFoods(String path ,Food [] foods){

        File foodFile = new File(path);
        Scanner foodScanner;
        try {
            foodScanner = new Scanner(foodFile);
        } catch (IOException ioException) {
            return;
        }

        for(int i = 0; foodScanner.hasNextLine(); i++){
            String [] foodInfo = foodScanner.nextLine().split("\t");
            foods[i] = new Food(Integer.parseInt(foodInfo[0]), foodInfo[1], Integer.parseInt(foodInfo[2]));
        }
        foodScanner.close();
    }

    /**
     * @return type of food, based on ID
     */
    public String getFoodType(){
        switch(foodID / 100){
            case 10:
                return "fruits";
            case 11:
                return "meal";
            case 12:
                return "dessert";
            default:
                return "nuts";
        }
    }

    public int getFoodID() {
        return foodID;
    }

    public String getNameOfFood() {
        return nameOfFood;
    }

    public int getCalorieCount() {
        return calorieCount;
    }
}
