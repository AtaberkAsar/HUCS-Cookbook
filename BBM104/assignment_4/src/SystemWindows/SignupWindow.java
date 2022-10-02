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
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SignupWindow extends Window {

    public SignupWindow(int maxError, String title, double discountPercentage, int blockTime,
                        Stage stage, Image logo, MediaPlayer errorPlayer,
                        HashMap<String, User> users, HashMap<String, Film> films,
                        HashMap<HallKey, Hall> halls, HashMap<Hall, ArrayList<SeatRecord>> seatRecords,
                        User currentUser) {

        super(maxError, title, discountPercentage, blockTime,
              stage, logo, errorPlayer, users, films, halls, seatRecords, currentUser);

    }


    /**
     * Signup Window
     * @param profilePicturePath Profile picture path
     */
    protected static void showWindow(String profilePicturePath) {

        Scene signupScene;

        ImageView profilePictureImageView = new ImageView(new Image(profilePicturePath));
        Label signupMessage = new Label("Welcome to the " + title + "!\n" +
                "Fill the form below and to create a new account.\n" +
                "You can go to Log In page by clicking LOG IN button."),
                username = new Label("Username:"),
                password = new Label("Password:"),
                passwordAgain = new Label("Retype Password:"),
                message = new Label("");
        TextField usernameTextField = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField passwordFieldAgain = new PasswordField();
        Button signupButton = new Button("SIGN UP"),
                loginButton = new Button("LOG IN"),
                profilePicture = new Button();
        GridPane signupGridPane = new GridPane();

        //region Buttons

        signupButton.setOnAction(e -> signup(usernameTextField, passwordField, passwordFieldAgain, message, profilePicturePath));
        loginButton.setOnAction(e -> LoginWindow.showWindow());
        profilePicture.setOnAction(e -> chooseProfilePicture());
        profilePictureImageView.setOnMouseClicked(e -> chooseProfilePicture());

        //endregion

        //region Cosmetics

        profilePictureImageView.setPreserveRatio(true);
        if (profilePictureImageView.getFitHeight() > profilePictureImageView.getFitWidth())
            profilePictureImageView.setFitHeight(100);
        else
            profilePictureImageView.setFitWidth(100);

        // profilePictureImageView.setFitHeight(profilePicture.getHeight());

        profilePicture.setGraphic(profilePictureImageView);

        signupMessage.setTextAlignment(TextAlignment.CENTER);

        usernameTextField.setMinWidth(200);
        passwordField.setMinWidth(200);
        passwordFieldAgain.setMinWidth(200);

        GridPane.setConstraints(profilePictureImageView, 0, 0, 3, 1);
        GridPane.setHalignment(profilePictureImageView, HPos.CENTER);
        GridPane.setConstraints(signupMessage, 0, 1, 3, 2);
        GridPane.setHalignment(signupMessage, HPos.CENTER);
        GridPane.setConstraints(username, 0, 3);
        GridPane.setConstraints(usernameTextField, 1, 3, 2, 1);
        GridPane.setConstraints(password, 0, 4);
        GridPane.setConstraints(passwordField, 1, 4, 2, 1);
        GridPane.setConstraints(passwordAgain, 0, 5);
        GridPane.setConstraints(passwordFieldAgain, 1, 5, 2, 1);
        GridPane.setConstraints(loginButton, 0, 6);
        GridPane.setConstraints(signupButton, 2, 6);
        GridPane.setHalignment(signupButton, HPos.RIGHT);
        GridPane.setConstraints(message, 0, 7, 3, 1);
        GridPane.setHalignment(message, HPos.CENTER);

        signupGridPane.getChildren().addAll(profilePictureImageView, signupMessage, username, usernameTextField,
                password, passwordField, passwordAgain, passwordFieldAgain,
                loginButton, signupButton, message);

        gridPaneAdjuster(signupGridPane, 8, 3);

        //endregion

        signupScene = new Scene(signupGridPane, 720, 480);

        stage.setScene(signupScene);
        stage.centerOnScreen();

    }


    /**
     * Signup validation method
     * @param usernameTextField Input from username text field
     * @param passwordField Input from password field
     * @param passwordFieldAgain Input from password field
     * @param message Message that will be displayed on the bottom of signupWindow
     */
    private static void signup(TextField usernameTextField, PasswordField passwordField, PasswordField passwordFieldAgain,
                        Label message, String profilePicturePath) {

        String username = usernameTextField.getText();
        String password = passwordField.getText();
        String passwordAgain = passwordFieldAgain.getText();

        if(username.equals("")) { // if username is empty
            message.setText("ERROR: Username cannot be empty");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
        }
        else if(username.equals("null")) { // if username equals null, since it breaks the backup.dat
            message.setText("ERROR: Username cannot be 'null'");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
        }
        else if(password.equals("")) { // if some password fields are empty
            message.setText("ERROR: Password cannot be empty!");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            return;
        }
        else if(users.containsKey(username)) { // if username already exists
            message.setText("ERROR: This username already exists!");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
        }
        else if(password.equals(passwordAgain)) { // if passwords equal
            message.setText("SUCCESS: You have successfully registered with your new credentials!");
            users.put(username, new User(username, Utils.hashPassword(password), false, false,
                    profilePicturePath, 0, 0));

        }
        else { // if passwords do not match
            message.setText("ERROR: Passwords do not match!");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
        }

        // Clearing text and password fields
        usernameTextField.clear();
        passwordField.clear();
        passwordFieldAgain.clear();
    }


    /**
     * Profile picture chooser method
     */
    private static void chooseProfilePicture() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Please choose a profile picture");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home") + "/desktop")); // opens desktop

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        ); // make sure only image files can be chosen

        File file = fileChooser.showOpenDialog(stage);
        String profilePicturePath;

        try {
            profilePicturePath = file.toURI().toURL().toExternalForm();
        } catch (MalformedURLException e) {
            return;
        }

        showWindow(profilePicturePath);
    }

}
