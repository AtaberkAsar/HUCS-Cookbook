package CinemaReservationSystem;

import SystemDataTypes.*;
import SystemWindows.SureWindow;
import SystemWindows.Window;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CinemaReservationSystem extends Application {
    private Stage stage;

    private int maxError;
    private String title;
    private double discountPercentage;
    private int blockTime;
    private Image logo;
    private MediaPlayer errorPlayer;
    private final int numberOfTry = 0;
    private Long now = null;
    private int clickCounter = 0;
    private final String luckySymbols = "♥♥♥☻♦◘"; // %14 win rate

    private HashMap<String, User> users = new HashMap<>();
    private HashMap<String, Film> films = new HashMap<>();
    private HashMap<HallKey, Hall> halls = new HashMap<>();
    private HashMap<Hall, ArrayList<SeatRecord>> seatRecords = new HashMap<>();

    private User currentUser = null;

    public CinemaReservationSystem() {

        //region Initializing the System, setting title, logo etc...
        Scanner propertiesScanner;
        try {
            propertiesScanner = new Scanner(new File(Utils.propertiesFilePath));
            propertiesScanner.nextLine(); // Skipping first 2 lines
            propertiesScanner.nextLine();
        } catch (FileNotFoundException e) {
            System.err.println("Properties file not found...");
            return;
        }
        while(propertiesScanner.hasNextLine()) {
            String[] property = propertiesScanner.nextLine().trim().split("=");
            switch(property[0]) {
                case "maximum-error-without-getting-blocked":
                    maxError = Integer.parseInt(property[1]);
                    break;
                case "title":
                    title = property[1];
                    break;
                case "discount-percentage":
                    discountPercentage = Double.parseDouble(property[1]);
                    break;
                case "block-time":
                    blockTime = Integer.parseInt(property[1]);
                    break;
                default:
                    System.err.printf("Unknown property: %s\n", property[0]);
                    break;
            }
        }
        propertiesScanner.close();
        logo = new Image(Utils.logoPath);

        Media errorSound = new Media(new File(Utils.errorSoundPath).toURI().toString());
        errorPlayer = new MediaPlayer(errorSound);

        // Loading from backup.dat
        Scanner backupScanner;
        Scanner luckyScanner;

        try {
            backupScanner = new Scanner(new File(Utils.backupFilePath));
        } catch (FileNotFoundException e) {
            System.err.println("Backup file not found...");
            return;
        }
        while(backupScanner.hasNextLine()){
            String[] backupLine = backupScanner.nextLine().trim().split("\t");
            switch(backupLine[0]) {
                case "user":
                    try {
                        luckyScanner = new Scanner(new File(Utils.luckyBackupPath)); // return to start of luckyBackup
                    } catch (FileNotFoundException fileNotFoundException) {
                        System.err.println("Profile picture backup not found...");
                        return;
                    }
                    // Workaround below implemented, since do while not working for some reason
                    String[] luckyLine = new String[] {"null"};
                    while(luckyScanner.hasNextLine()) {
                        if(luckyLine[0].equals(backupLine[1]))
                            break;
                        luckyLine = luckyScanner.nextLine().trim().split("\t");
                    }

                    users.put(backupLine[1], new User(backupLine[1],
                                                      backupLine[2],
                                                      backupLine[3].equals("true"),
                                                      backupLine[4].equals("true"),
                                                      luckyLine[1],
                                                      Integer.parseInt(luckyLine[2]),
                                                      Integer.parseInt(luckyLine[3])));
                    break;
                case "film":
                    films.put(backupLine[1], new Film(backupLine[1],
                                                      backupLine[2],
                                                      backupLine[3]));
                    break;
                case "hall":
                    halls.put(new HallKey(films.get(backupLine[1]), backupLine[2]), new Hall(films.get(backupLine[1]),
                                                                                                backupLine[2],
                                                                                                Integer.parseInt(backupLine[3]),
                                                                                                Integer.parseInt(backupLine[4]),
                                                                                                Integer.parseInt(backupLine[5])));
                    break;
                case "seat":
                    Film tempFilm = films.get(backupLine[1]);
                    HallKey tempHallKey = new HallKey(tempFilm, backupLine[2]);
                    Hall tempHall = halls.get(tempHallKey);

                    ArrayList<SeatRecord> seats = seatRecords.get(tempHall);
                    if(seats == null)
                        seats = new ArrayList<>();
                    seats.add(new SeatRecord(tempFilm,
                                                tempHall,
                                                Integer.parseInt(backupLine[3]),
                                                Integer.parseInt(backupLine[4]),
                                                backupLine[5].equals("null") ? null : users.get(backupLine[5]),
                                                Integer.parseInt(backupLine[6])));
                    seatRecords.put(tempHall, seats);
                    break;
            }
        }
        //endregion
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle(title);
        stage.getIcons().add(logo);
        stage.setOnCloseRequest(e -> {
            e.consume();
            SureWindow.showWindow();
        });


        Window window = new Window(maxError, title, discountPercentage, blockTime,
                                   stage, logo, errorPlayer, users, films, halls, seatRecords, currentUser);
        window.startSystem();
        stage.show();
    }


}