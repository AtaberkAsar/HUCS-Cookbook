package SystemWindows;

import SystemDataTypes.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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

public class RemoveHallWindow extends Window {

    public RemoveHallWindow(int maxError, String title, double discountPercentage, int blockTime,
                            Stage stage, Image logo, MediaPlayer errorPlayer,
                            HashMap<String, User> users, HashMap<String, Film> films,
                            HashMap<HallKey, Hall> halls, HashMap<Hall, ArrayList<SeatRecord>> seatRecords,
                            User currentUser) {

        super(maxError, title, discountPercentage, blockTime,
              stage, logo, errorPlayer, users, films, halls, seatRecords, currentUser);

    }


    /**
     * Remove Hall Window
     * @param film Film that the hall will be removed from
     */
    protected static void showWindow(Film film) {
        Scene removeHallScene;
        boolean flag = true;

        Label removeHallMessage = new Label("Select the hall that you desire to remove from " +
                film.getFilmName() + " and then click OK.");
        ComboBox<String> hallComboBox = new ComboBox<>();
        Button backButton = new Button("\u25C2 BACK"),
                okButton = new Button("OK");
        GridPane removeHallGridPane = new GridPane();

        //region Buttons

        // adding halls to hall comboBox
        for(Hall hall: halls.values())
            if(hall.getFilm().equals(film)) {
                hallComboBox.getItems().add(hall.getHallName());
                if(flag) {
                    hallComboBox.setValue(hall.getHallName());
                    flag = false;
                }
            }

        backButton.setOnAction(e -> FilmWindow.showWindow(film.getFilmName()));
        okButton.setOnAction(e -> removeHall(film, hallComboBox.getValue()));

        //endregion

        //region Cosmetics

        removeHallMessage.setTextAlignment(TextAlignment.CENTER);

        removeHallGridPane.setAlignment(Pos.CENTER);
        removeHallGridPane.setPadding(new Insets(20));
        removeHallGridPane.setVgap(20);
        removeHallGridPane.setHgap(20);

        GridPane.setConstraints(removeHallMessage, 0, 0, 2, 1);
        GridPane.setHalignment(removeHallMessage, HPos.CENTER);
        GridPane.setConstraints(hallComboBox, 0, 1, 2 , 1);
        GridPane.setHalignment(hallComboBox, HPos.CENTER);
        GridPane.setConstraints(backButton, 0, 2);
        GridPane.setHalignment(backButton, HPos.RIGHT);
        GridPane.setConstraints(okButton, 1, 2);

        removeHallGridPane.getChildren().addAll(removeHallMessage, hallComboBox, backButton, okButton);

        gridPaneAdjuster(removeHallGridPane, 3, 2);

        hallComboBox.setMaxWidth(200);

        removeHallScene = new Scene(removeHallGridPane);

        //endregion

        stage.setScene(removeHallScene);
        stage.centerOnScreen();

    }


    /**
     * Hall removing method
     * @param film Film which the hall will be removed from
     * @param hallName Hall name that will be removed
     */
    private static void removeHall(Film film, String hallName) {

        HallKey hallKey = new HallKey(film, hallName);
        Hall hallToDel = halls.get(hallKey);

        seatRecords.remove(hallToDel);
        halls.remove(hallKey);
        showWindow(film);

    }

}
