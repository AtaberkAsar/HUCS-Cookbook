package SystemWindows;

import CinemaReservationSystem.Utils;

import SystemDataTypes.*;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginWindow extends Window {

    protected LoginWindow(int maxError, String title, double discountPercentage, int blockTime,
                       Stage stage, Image logo, MediaPlayer errorPlayer,
                       HashMap<String, User> users, HashMap<String, Film> films,
                       HashMap<HallKey, Hall> halls, HashMap<Hall, ArrayList<SeatRecord>> seatRecords,
                       User currentUser) {

        super(maxError, title, discountPercentage, blockTime, stage, logo, errorPlayer,
              users, films, halls, seatRecords, currentUser);

    }

    /**
     * Login window
     */
    protected static void showWindow() {

        Scene loginScene;

        Label welcomeMessage = new Label("Welcome to the " + title + "!\n" +
                "Please enter your credentials below and click LOGIN.\n" +
                "You can create a new account by clicking SIGN UP button."),
                username = new Label("Username:"),
                password = new Label("Password:"),
                message = new Label("");
        TextField usernameTextField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button signupButton = new Button("SIGN UP"),
                loginButton = new Button("LOG IN");
        GridPane loginGridPane = new GridPane();

        //region Buttons
        signupButton.setOnAction(e -> SignupWindow.showWindow(Utils.logoPath));

        loginButton.setOnAction(e -> login(usernameTextField, passwordField, message));

        usernameTextField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER)
                loginButton.fire();
        });

        passwordField.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.ENTER)
                loginButton.fire();
        });
        //endregion

        //region Cosmetics
        welcomeMessage.setTextAlignment(TextAlignment.CENTER);

        usernameTextField.setPromptText("username");
        usernameTextField.setMinWidth(200);
        passwordField.setPromptText("********");
        passwordField.setMinWidth(200);

        GridPane.setConstraints(welcomeMessage, 0, 0, 3, 3);
        GridPane.setHalignment(welcomeMessage, HPos.CENTER);
        GridPane.setConstraints(username, 0, 3);
        GridPane.setConstraints(usernameTextField, 1, 3, 2, 1);
        GridPane.setConstraints(password, 0, 4);
        GridPane.setConstraints(passwordField, 1, 4, 2, 1);
        GridPane.setConstraints(signupButton, 0, 5);
        GridPane.setConstraints(loginButton, 2, 5);
        GridPane.setHalignment(loginButton, HPos.RIGHT);
        GridPane.setConstraints(message, 0, 6, 3, 1);
        GridPane.setHalignment(message, HPos.CENTER);

        loginGridPane.getChildren().addAll(welcomeMessage, username, usernameTextField, password, passwordField,
                signupButton, loginButton, message);

        gridPaneAdjuster(loginGridPane, 7, 3);

        //endregion

        loginScene = new Scene(loginGridPane);

        stage.setScene(loginScene);
        stage.centerOnScreen();
    }


    /**
     * Login validation method
     * @param usernameTextField Input from username text field
     * @param passwordField Input from password field
     * @param message Message that will be displayed on the bottom of loginWindow
     */
    private static void login(TextField usernameTextField, PasswordField passwordField, Label message){

        if(now != null) { // if timeout
            long tempNow = System.currentTimeMillis();
            int secondsPassed = (int) ((tempNow - now) / 1000);
            if(secondsPassed < blockTime) { // checks if timeout
                message.setText("ERROR: Please wait until end of the " + (blockTime - secondsPassed) +
                        " seconds to make a new operation!");
                errorPlayer.seek(errorPlayer.getStartTime());
                errorPlayer.play();
                return;
            }
            else
                now = null; // end of timeout
        }

        String usernameInput = usernameTextField.getText(),
                passwordInput = passwordField.getText();

        if(usernameInput.equals("")) { // if username is empty
            numberOfTry++;
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            message.setText("ERROR: Username cannot be empty!");
        }
        else if(users.get(usernameInput) == null) { // if username does not exist in the database
            numberOfTry++;
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            message.setText("ERROR: There is no such a credential!");
        }
        else if(users.get(usernameInput).getHashedPassword().equals(Utils.hashPassword(passwordInput))) { // login Successfully
            numberOfTry = 0;
            currentUser = users.get(usernameInput);
            WelcomeWindow.showWindow();
        }
        else { // if unsuccessful login
            numberOfTry++;
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            message.setText("ERROR: There is no such a credential!");
        }

        if(numberOfTry == maxError) { // if no more try left
            now = System.currentTimeMillis();
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            message.setText("ERROR: Please wait for " + blockTime + " seconds to make a new operation!");
            numberOfTry = 0;
        }

        passwordField.clear();
    }

}
