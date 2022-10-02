package SystemWindows;

import SystemDataTypes.*;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class RemoveFilmWindow extends Window {

    public RemoveFilmWindow(int maxError, String title, double discountPercentage, int blockTime, Stage stage, Image logo, MediaPlayer errorPlayer, HashMap<String, User> users, HashMap<String, Film> films, HashMap<HallKey, Hall> halls, HashMap<Hall, ArrayList<SeatRecord>> seatRecords, User currentUser) {

        super(maxError, title, discountPercentage, blockTime, stage, logo, errorPlayer, users, films, halls, seatRecords, currentUser);

    }


    /**
     * Remove Film Window
     */
    protected static void showWindow(){

        Scene removeFilmScene;
        boolean flag = true;

        Label removeFilmMessage = new Label("Select the film that you desired to remove and then click OK.");
        ComboBox<String> filmComboBox = new ComboBox<>();
        Button backButton = new Button("\u25C2 BACK"),
                okButton = new Button("OK");
        GridPane removeFilmGridPane = new GridPane();

        //region Buttons

        // adding films to combo box
        for(String filmName: films.keySet()) {
            filmComboBox.getItems().add(filmName);
            if(flag) {
                filmComboBox.setValue(filmName);
                flag = false;
            }
        }

        backButton.setOnAction(e -> WelcomeWindow.showWindow());
        okButton.setOnAction(e -> removeFilm(filmComboBox.getValue()));

        //endregion

        //region Cosmetics

        removeFilmMessage.setTextAlignment(TextAlignment.CENTER);

        GridPane.setConstraints(removeFilmMessage, 0, 0, 2, 1);
        GridPane.setHalignment(removeFilmMessage, HPos.CENTER);
        GridPane.setConstraints(filmComboBox, 0, 1, 2, 1);
        GridPane.setConstraints(backButton, 0, 2);
        GridPane.setConstraints(okButton, 1, 2);
        GridPane.setHalignment(okButton, HPos.RIGHT);

        removeFilmGridPane.getChildren().addAll(removeFilmMessage, filmComboBox, backButton, okButton);

        gridPaneAdjuster(removeFilmGridPane, 3, 2);

        //endregion

        removeFilmScene = new Scene(removeFilmGridPane);
        stage.setScene(removeFilmScene);
        stage.centerOnScreen();

    }


    /**
     * Film removing method
     * @param filmName Film name that will be removed
     */
    private static void removeFilm(String filmName) {

        Film delFilm = films.get(filmName);
        ArrayList<Hall> hallsToDel = new ArrayList<>();

        for(Hall hall: halls.values())
            if(hall.getFilm().equals(delFilm)) {
                seatRecords.remove(hall);
                hallsToDel.add(hall);
            }

        for(Hall hall: hallsToDel)
            halls.remove(new HallKey(delFilm, hall.getHallName()));
        films.remove(filmName);

        RemoveFilmWindow.showWindow();

    }

}
