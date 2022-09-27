import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Sport {
    private int sportID;
    private String nameOfSport;
    private int calorieBurned;

    /**
     * @param sportID ID of the sport
     * @param nameOfSport name of the sport
     * @param calorieBurned calories burned in 60 min
     */
    public Sport(int sportID, String nameOfSport, int calorieBurned){
        this.sportID = sportID;
        this.nameOfSport = nameOfSport;
        this.calorieBurned = calorieBurned;
    }

    /**
     * reads given sport file
     * @param path path of the sport file
     * @param sports Sport [], that information from the file will be written into
     */
    public static void readSports(String path ,Sport [] sports){

        File sportFile = new File(path);
        Scanner sportScanner;
        try {
            sportScanner = new Scanner(sportFile);
        } catch (IOException ioException) {
            return;
        }

        for(int i = 0; sportScanner.hasNextLine(); i++){
            String [] sportInfo = sportScanner.nextLine().split("\t");
            sports[i] = new Sport(Integer.parseInt(sportInfo[0]), sportInfo[1], Integer.parseInt(sportInfo[2]));
        }
        sportScanner.close();
    }

    public int getSportID() {
        return sportID;
    }

    public String getNameOfSport() {
        return nameOfSport;
    }

    public int getCalorieBurned() {
        return calorieBurned;
    }

}
