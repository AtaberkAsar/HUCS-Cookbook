package SystemWindows;

import CinemaReservationSystem.Utils;
import SystemDataTypes.*;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class FilmWindow extends Window {

    public FilmWindow(int maxError, String title, double discountPercentage, int blockTime,
                      Stage stage, Image logo, MediaPlayer errorPlayer,
                      HashMap<String, User> users, HashMap<String, Film> films,
                      HashMap<HallKey, Hall> halls, HashMap<Hall, ArrayList<SeatRecord>> seatRecords,
                      User currentUser) {

        super(maxError, title, discountPercentage, blockTime,
              stage, logo, errorPlayer, users, films, halls, seatRecords, currentUser);

    }


    /**
     * Film Window
     * @param filmName Film name
     */
    protected static void showWindow(String filmName) {

        Scene filmScene;
        boolean flag = true;


        Film film = films.get(filmName);

        if (film == null) {
            System.err.println("No more film left in the system, or existing films cannot be found!");
            return;
        }

        String trailerPath = Utils.trailersFolderPath + film.getTrailerPath();
        Media trailer = new Media(new File(trailerPath).toURI().toString());
        MediaPlayer trailerPlayer = new MediaPlayer(trailer);

        Label filmLabel = new Label(film.getFilmName() + " (" + film.getDuration() + " minutes)");
        MediaView trailerView = new MediaView(trailerPlayer);
        Button playButton = new Button("\u25B8"),
                forwardButton = new Button(">>"),
                backwardButton = new Button("<<"),
                resetButton = new Button("|<<"),
                backButton = new Button("\u25C2 BACK"),
                okButton = new Button("OK"),
                addHallButton = new Button("Add Hall"),
                removeHallButton = new Button("Remove Hall");
        Slider volumeController = new Slider();
        ComboBox<String> hallComboBox = new ComboBox<>();
        GridPane filmGridPane = new GridPane();

        //region Buttons

        // adding halls to combo box
        for(Hall hall: halls.values())
            if(hall.getFilm().equals(films.get(filmName))) {
                hallComboBox.getItems().add(hall.getHallName());
                if(flag) { // choose first value for combo box
                    hallComboBox.setValue(hall.getHallName());
                    flag = false;
                }
            }

        playButton.setOnAction(e -> {
            playButton.setText(playButton.getText().equals("\u25B8") ? "||" : "\u25B8");
            if(playButton.getText().equals("\u25B8"))
                trailerPlayer.pause();
            else {
                trailerPlayer.play();
                trailerPlayer.setVolume(volumeController.getValue());
            }
        });
        forwardButton.setOnAction(e -> trailerPlayer.seek(new Duration(trailerPlayer.getCurrentTime().toMillis() + 5000)));
        backwardButton.setOnAction(e -> trailerPlayer.seek(new Duration(trailerPlayer.getCurrentTime().toMillis() - 5000)));
        resetButton.setOnAction(e -> trailerPlayer.seek(trailerPlayer.getStartTime()));
        backButton.setOnAction(e -> {
            trailerPlayer.seek(trailerPlayer.getStartTime());
            trailerPlayer.pause();
            WelcomeWindow.showWindow();
        });
        okButton.setOnAction(e -> {
            trailerPlayer.seek(trailerPlayer.getStartTime());
            trailerPlayer.pause();
            if(currentUser.getAdminStatus())
                HallWindow.showAdminHall(hallComboBox.getValue(), film, new Label(), currentUser.getUserName());
            else
                HallWindow.showClientHall(hallComboBox.getValue(), film, new Label());
        });
        addHallButton.setOnAction(e -> {
            trailerPlayer.seek(trailerPlayer.getStartTime());
            trailerPlayer.pause();
            AddHallWindow.showWindow(film);
        });
        removeHallButton.setOnAction(e -> {
            trailerPlayer.seek(trailerPlayer.getStartTime());
            trailerPlayer.pause();
            RemoveHallWindow.showWindow(film);
        });

        // adjust volume controller
        volumeController.setMax(1);
        volumeController.setMin(0);
        volumeController.setValue(0.1);
        volumeController.valueProperty().addListener((v, oldValue, newValue) -> trailerPlayer.setVolume((double) newValue));

        //endregion

        //region Cosmetics
        filmLabel.setTextAlignment(TextAlignment.CENTER);

        trailerView.setPreserveRatio(true);
        trailerView.fitWidthProperty().bind(stage.widthProperty().multiply(0.5));

        volumeController.setOrientation(Orientation.VERTICAL);
        volumeController.setCenterShape(true);

        GridPane.setConstraints(filmLabel, 0, 0, 8, 1);
        GridPane.setHalignment(filmLabel, HPos.CENTER);
        GridPane.setConstraints(trailerView, 0, 0, 7, 8);
        GridPane.setHalignment(trailerView, HPos.CENTER);
        GridPane.setConstraints(playButton, 7, 1);
        GridPane.setHalignment(playButton, HPos.CENTER);
        GridPane.setConstraints(backwardButton, 7, 2);
        GridPane.setHalignment(backwardButton, HPos.CENTER);
        GridPane.setConstraints(forwardButton, 7, 3);
        GridPane.setHalignment(forwardButton, HPos.CENTER);
        GridPane.setConstraints(resetButton, 7, 4);
        GridPane.setHalignment(resetButton, HPos.CENTER);
        GridPane.setConstraints(volumeController, 7, 5, 1, 3);
        GridPane.setHalignment(volumeController, HPos.CENTER);
        GridPane.setConstraints(backButton, 1, 8);
        GridPane.setHalignment(backButton, HPos.CENTER);
        GridPane.setConstraints(addHallButton, 2, 8);
        GridPane.setHalignment(addHallButton, HPos.CENTER);
        GridPane.setConstraints(removeHallButton, 3, 8);
        GridPane.setHalignment(removeHallButton, HPos.CENTER);
        GridPane.setConstraints(hallComboBox, 4, 8, 2, 1);
        GridPane.setConstraints(okButton, 6, 8);

        filmGridPane.getChildren().addAll(trailerView, filmLabel, playButton, backwardButton, forwardButton, resetButton,
                volumeController, backButton, hallComboBox, okButton);

        if(currentUser.getAdminStatus()) {
            filmGridPane.getChildren().addAll(addHallButton, removeHallButton);
        }

        gridPaneAdjuster(filmGridPane, 9, 8);

        //endregion

        filmScene = new Scene(filmGridPane, 1080, 720);

        stage.setScene(filmScene);
        stage.centerOnScreen();

    }


}
