package SystemWindows;

import CinemaReservationSystem.Utils;
import SystemDataTypes.*;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Window {
    protected static int maxError;
    protected static String title;
    protected static double discountPercentage;
    protected static int blockTime;

    protected static Stage stage;
    protected static Image logo;
    protected static MediaPlayer errorPlayer;

    protected static int numberOfTry = 0;
    protected static Long now = null;
    protected static int clickCounter = 0;
    protected static final String luckySymbols = "\u2665\u2665\u2665\u2666\u25B4\u2720"; // %14 win rate

    protected static HashMap<String, User> users = new HashMap<>();
    protected static HashMap<String, Film> films = new HashMap<>();
    protected static HashMap<HallKey, Hall> halls = new HashMap<>();
    protected static HashMap<Hall, ArrayList<SeatRecord>> seatRecords = new HashMap<>();

    protected static User currentUser = null;

    public Window(int maxError, String title, double discountPercentage, int blockTime,
                  Stage stage, Image logo, MediaPlayer errorPlayer,
                  HashMap<String, User> users, HashMap<String, Film> films,
                  HashMap<HallKey, Hall> halls, HashMap<Hall, ArrayList<SeatRecord>> seatRecords,
                  User currentUser) {

        Window.maxError = maxError;
        Window.title = title;
        Window.discountPercentage = discountPercentage;
        Window.blockTime = blockTime;

        Window.stage = stage;
        Window.logo = logo;
        Window.errorPlayer = errorPlayer;

        Window.users = users;
        Window.films = films;
        Window.halls = halls;
        Window.seatRecords = seatRecords;

        Window.currentUser = currentUser;
    }

    public void startSystem(){
        LoginWindow.showWindow();
    }

    protected static void gridPaneAdjuster(GridPane gridPane, int row, int col) {

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100);
        rowConstraints.setMinHeight(20);

        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100);
        columnConstraints.setMinWidth(50);

        for(int i = 0; i < row; i++)
            gridPane.getRowConstraints().add(rowConstraints);

        for(int i = 0; i < col; i++)
            gridPane.getColumnConstraints().add(columnConstraints);

        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(20);
        gridPane.setHgap(20);
        gridPane.setPadding(new Insets(20));

        // Below line used for debugging purposes...
        // gridPane.setGridLinesVisible(true);
    }

    /**
     * Close and save method
     */
    protected static void closeEverything() {

        File backupFile = new File(Utils.backupFilePath);
        PrintStream backupWriter;

        File luckyFile = new File(Utils.luckyBackupPath);
        PrintStream luckyWriter;

        try {
            backupWriter = new PrintStream(backupFile);
            luckyWriter = new PrintStream(luckyFile);
        } catch (FileNotFoundException e) {
            System.err.println("backupFile not found");
            return;
        }
        for(User user: users.values()) {
            backupWriter.println(user);
            luckyWriter.println(user.luckyBackup());
        }

        for(Film film: films.values()) {
            backupWriter.println(film);
            for(Hall hall: halls.values())
                if(hall.getFilm().equals(film)) {
                    backupWriter.println(hall);
                    for(SeatRecord seat: seatRecords.get(hall))
                        backupWriter.println(seat);
                }
        }

        backupWriter.flush();
        backupWriter.close();
        luckyWriter.flush();
        luckyWriter.close();
    }



}
