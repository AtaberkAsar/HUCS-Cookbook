package SystemWindows;

import SystemDataTypes.*;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class WelcomeWindow extends Window {

    public WelcomeWindow(int maxError, String title, double discountPercentage, int blockTime,
                         Stage stage, Image logo, MediaPlayer errorPlayer,
                         HashMap<String, User> users, HashMap<String, Film> films,
                         HashMap<HallKey, Hall> halls, HashMap<Hall, ArrayList<SeatRecord>> seatRecords,
                         User currentUser) {

        super(maxError, title, discountPercentage, blockTime,
              stage, logo, errorPlayer, users, films, halls, seatRecords, currentUser);

    }


    /**
     * Welcome Window
     */
    protected static void showWindow() {

        Scene welcomeScene;
        boolean flag = true;

        ImageView profilePicture = new ImageView(new Image(currentUser.getProfilePicturePath()));
        String welcome = String.format("Welcome %s%s%s%s%s%s!\n%s",
                currentUser.getUserName(),
                currentUser.getAdminStatus() || currentUser.getClubMemberStatus() ? " (" : "",
                currentUser.getAdminStatus() ? "Admin" : "",
                currentUser.getAdminStatus() && currentUser.getClubMemberStatus() ? " - " : "",
                currentUser.getClubMemberStatus() ? "Club Member" : "",
                currentUser.getAdminStatus() || currentUser.getClubMemberStatus() ? ")" : "",
                currentUser.getAdminStatus() ? "You can either select film below or do edits" :
                        "Select a film and then click OK to continue.");
        Label welcomeMessage = new Label(welcome);
        ComboBox<String> filmComboBox = new ComboBox<>();
        Button okButton = new Button("OK"),
                logoutButton = new Button("LOG OUT"),
                addFilmButton = new Button("Add Film"),
                removeFilmButton = new Button("Remove Film"),
                editUsersButton = new Button("Edit Users");
        GridPane welcomeGridPane = new GridPane();

        //region Buttons

        // adding films to comboBox
        for(String film: films.keySet()) {
            filmComboBox.getItems().add(film);
            if(flag) {
                filmComboBox.setValue(film);
                flag = false;
            }
        }

        // adding extra feature
        profilePicture.setOnMouseClicked(e -> LuckyWindow.luckyMethod());

        logoutButton.setOnAction(e -> {
            currentUser = null;
            LoginWindow.showWindow();
        });
        okButton.setOnAction(e -> FilmWindow.showWindow(filmComboBox.getValue()));
        addFilmButton.setOnAction(e -> AddFilmWindow.showWindow());
        removeFilmButton.setOnAction(e -> RemoveFilmWindow.showWindow());
        editUsersButton.setOnAction(e -> EditUsersWindow.showWindow());

        //endregion

        //region Cosmetics

        welcomeMessage.setTextAlignment(TextAlignment.CENTER);

        profilePicture.setPreserveRatio(true);
        if (profilePicture.getFitHeight() > profilePicture.getFitWidth())
            profilePicture.setFitHeight(100);
        else
            profilePicture.setFitWidth(100);

        GridPane.setConstraints(profilePicture, 0, 0, 3, 1);
        GridPane.setHalignment(profilePicture, HPos.CENTER);
        GridPane.setConstraints(welcomeMessage, 0, 1, 3, 2);
        GridPane.setHalignment(welcomeMessage, HPos.CENTER);
        GridPane.setConstraints(filmComboBox, 0, 3, 2, 1);
        GridPane.setConstraints(okButton, 2, 3);
        GridPane.setHalignment(okButton, HPos.RIGHT);
        GridPane.setConstraints(addFilmButton, 0, 4);
        GridPane.setHalignment(addFilmButton, HPos.RIGHT);
        GridPane.setConstraints(removeFilmButton, 1, 4);
        GridPane.setHalignment(removeFilmButton, HPos.CENTER);
        GridPane.setConstraints(editUsersButton, 2, 4);
        GridPane.setConstraints(logoutButton, 0, 5, 3, 1);
        GridPane.setHalignment(logoutButton, HPos.RIGHT);

        welcomeGridPane.getChildren().addAll(profilePicture, welcomeMessage, filmComboBox, okButton, logoutButton);

        if(currentUser.getAdminStatus())
            welcomeGridPane.getChildren().addAll(addFilmButton, removeFilmButton, editUsersButton);

        gridPaneAdjuster(welcomeGridPane, 6, 3);

        //endregion

        welcomeScene = new Scene(welcomeGridPane, 640, 360);
        stage.setScene(welcomeScene);
        stage.centerOnScreen();

    }

}
