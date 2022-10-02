package SystemWindows;

import CinemaReservationSystem.Utils;
import SystemDataTypes.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class AddFilmWindow extends Window {

    public AddFilmWindow(int maxError, String title, double discountPercentage, int blockTime,
                         Stage stage, Image logo, MediaPlayer errorPlayer,
                         HashMap<String, User> users, HashMap<String, Film> films,
                         HashMap<HallKey, Hall> halls, HashMap<Hall, ArrayList<SeatRecord>> seatRecords,
                         User currentUser) {

        super(maxError, title, discountPercentage, blockTime,
              stage, logo, errorPlayer, users, films, halls, seatRecords, currentUser);

    }


    /**
     * Add Film Window
     */
    protected static void showWindow(){
        Scene addFilmScene;

        Label addFilmMessage = new Label("Please give name, relative path of the trailer and duration of the film."),
                name = new Label("Name:"),
                trailerPath = new Label("Trailer (Path):"),
                duration = new Label("Duration (m):"),
                errorMessage = new Label();
        TextField nameField = new TextField(),
                pathField = new TextField(),
                durationField = new TextField();
        Button backButton = new Button("\u25C2 BACK"),
                okButton = new Button("OK");
        GridPane addFilmGridPane = new GridPane();

        //region Buttons

        backButton.setOnAction(e -> WelcomeWindow.showWindow());
        okButton.setOnAction(e -> addFilm(nameField, pathField, durationField, errorMessage));

        //endregion

        //region Cosmetics

        addFilmMessage.setTextAlignment(TextAlignment.CENTER);

        addFilmGridPane.setAlignment(Pos.CENTER);
        addFilmGridPane.setHgap(20);
        addFilmGridPane.setVgap(20);
        addFilmGridPane.setPadding(new Insets(20));

        GridPane.setConstraints(addFilmMessage, 0, 0, 2, 1);
        GridPane.setHalignment(addFilmMessage, HPos.CENTER);
        GridPane.setConstraints(name, 0, 1);
        GridPane.setConstraints(nameField, 1, 1);
        GridPane.setConstraints(trailerPath, 0, 2);
        GridPane.setConstraints(pathField, 1, 2);
        GridPane.setConstraints(duration, 0, 3);
        GridPane.setConstraints(durationField, 1, 3);
        GridPane.setConstraints(backButton, 0, 4);
        GridPane.setConstraints(okButton, 1, 4);
        GridPane.setHalignment(okButton, HPos.RIGHT);
        GridPane.setConstraints(errorMessage, 0, 5, 2, 1);
        GridPane.setHalignment(errorMessage, HPos.CENTER);

        addFilmGridPane.getChildren().addAll(addFilmMessage, name, nameField, trailerPath, pathField,
                duration, durationField, backButton, okButton, errorMessage);

        gridPaneAdjuster(addFilmGridPane, 6, 2);

        // endregion

        addFilmScene = new Scene(addFilmGridPane);
        stage.setScene(addFilmScene);
        stage.centerOnScreen();

    }

    /**
     * New film addition method
     * @param filmField Film name
     * @param pathField Trailer path of the new film, relative to the trailers path
     * @param durationField Duration of the film
     * @param errorMessage Error message that will be displayed at the bottom of addFilmWindow
     */
    private static void addFilm(TextField filmField, TextField pathField, TextField durationField, Label errorMessage){

        String filmName = filmField.getText(),
               path = pathField.getText(),
               duration = durationField.getText();


        String trailerPath = Utils.trailersFolderPath + path;
        File trailerFile = new File(trailerPath);
        boolean flag = false;

        try {
            int intDuration = Integer.parseInt(duration);
            if (intDuration <= 0)
                throw new NumberFormatException();
        } catch(NumberFormatException numberFormatException) {
            flag = true;
        }

        if(filmName.equals("")) { // if film name is empty
            errorMessage.setText("Error: Name cannot be empty!");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            return;
        }
        else if(path.equals("")) { // if path is empty
            errorMessage.setText("Error: Trailer path cannot be empty!");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            return;
        }
        else if(flag) { // if duration is not positive integer
            errorMessage.setText("Error: Duration has to be a positive integer!");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            return;
        }
        else if(films.containsKey(filmName)) { // if film already exists
            errorMessage.setText("Error: This film already exists!");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            return;
        }
        else if(!trailerFile.exists()) { // if trailer path cannot be found
            errorMessage.setText("Error: There is no such a trailer!");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            return;
        }

        films.put(filmName, new Film(filmName, path, duration));
        errorMessage.setText("SUCCESS: Film added successfully!");

        filmField.clear();
        pathField.clear();
        durationField.clear();

    }


}
