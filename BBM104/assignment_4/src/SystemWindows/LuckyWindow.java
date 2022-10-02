package SystemWindows;

import SystemDataTypes.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class LuckyWindow extends Window {

    public LuckyWindow(int maxError, String title, double discountPercentage, int blockTime,
                       Stage stage, Image logo, MediaPlayer errorPlayer,
                       HashMap<String, User> users, HashMap<String, Film> films,
                       HashMap<HallKey, Hall> halls, HashMap<Hall, ArrayList<SeatRecord>> seatRecords,
                       User currentUser) {

        super(maxError, title, discountPercentage, blockTime,
              stage, logo, errorPlayer, users, films, halls, seatRecords, currentUser);

    }


    /**
     * Lucky Window
     */
    protected static void showWindow() {

        Scene luckyScene;

        Label luckyWelcome = new Label("Do you feel lucky?"),
                message = new Label("");
        Button buttonLeft = new Button("\u2665"),
                buttonMiddle = new Button("\u2665"),
                buttonRight = new Button("\u2665"),
                buttonYes = new Button("YES"),
                buttonNo = new Button("NO");
        GridPane luckyGridPane = new GridPane();

        //region Buttons
        buttonLeft.setDisable(true);
        buttonMiddle.setDisable(true);
        buttonRight.setDisable(true);

        buttonYes.setOnAction(e -> {
            buttonYes.setDisable(true);
            feelLucky(buttonLeft, buttonMiddle, buttonRight, message, buttonYes);
        });
        buttonNo.setOnAction(e -> WelcomeWindow.showWindow());

        //endregion

        //region Cosmetics

        buttonLeft.setStyle("-fx-border-color: transparent; -fx-border-width: 0; -fx-font-size: 36;" +
                "-fx-background-radius: 0; -fx-background-color: transparent;");
        buttonMiddle.setStyle("-fx-border-color: transparent; -fx-border-width: 0; -fx-font-size: 36;" +
                "-fx-background-radius: 0; -fx-background-color: transparent;");
        buttonRight.setStyle("-fx-border-color: transparent; -fx-border-width: 0; -fx-font-size: 36;" +
                "-fx-background-radius: 0; -fx-background-color: transparent;");

        message.setTextAlignment(TextAlignment.CENTER);

        GridPane.setConstraints(luckyWelcome, 0, 0, 3, 1);
        GridPane.setHalignment(luckyWelcome, HPos.CENTER);
        GridPane.setConstraints(buttonLeft, 0, 1);
        GridPane.setHalignment(buttonLeft, HPos.RIGHT);
        GridPane.setConstraints(buttonMiddle, 1, 1);
        GridPane.setHalignment(buttonMiddle, HPos.CENTER);
        GridPane.setConstraints(buttonRight, 2, 1);
        GridPane.setConstraints(buttonYes, 0, 2);
        GridPane.setHalignment(buttonYes, HPos.RIGHT);
        GridPane.setConstraints(buttonNo, 2, 2);
        GridPane.setConstraints(message, 0, 3, 3, 1);
        GridPane.setHalignment(message, HPos.CENTER);

        luckyGridPane.getChildren().addAll(luckyWelcome, buttonLeft, buttonMiddle, buttonRight, buttonYes, buttonNo, message);

        gridPaneAdjuster(luckyGridPane, 4, 3);
        //endregion

        luckyScene = new Scene(luckyGridPane, 640, 360);

        stage.setScene(luckyScene);
        stage.centerOnScreen();
    }


    /**
     * Method to open luckyWindow
     */
    protected static void luckyMethod() {
        if (now != null) {
            if ((System.currentTimeMillis() - now) <= 2000 && clickCounter < 5)
                clickCounter++;
            else {
                now = null;
                clickCounter = 0;
            }

            if(clickCounter >= 5)
                LuckyWindow.showWindow();
        }
        else {
            now = System.currentTimeMillis();
        }
    }

    /**
     * Feel lucky method, to try user's luck
     * @param buttonLeft Left button
     * @param buttonMiddle Middle button
     * @param buttonRight Right button
     * @param message Message that will be displayed at the bottom of lucky window
     * @param buttonYes Yes button
     */
    private static void feelLucky(Button buttonLeft, Button buttonMiddle, Button buttonRight, Label message, Button buttonYes) {
        int ticketCount = currentUser.getTicketCounter();
        if (ticketCount < 5) {
            message.setText("You need at least 5 tickets to try your luck.\n Current Ticket Count: " + ticketCount);
            return;
        }

        currentUser.setTicketCounter(ticketCount - 5);

        Random random = new Random();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(350), e -> {
            buttonLeft.setText(String.valueOf(luckySymbols.charAt(random.nextInt(6))));
            buttonMiddle.setText(String.valueOf(luckySymbols.charAt(random.nextInt(6))));
            buttonRight.setText(String.valueOf(luckySymbols.charAt(random.nextInt(6))));
        }));
        timeline.setCycleCount(5);
        timeline.play();
        timeline.setOnFinished(e -> {
            buttonYes.setDisable(false);
            if(buttonLeft.getText().equals(buttonMiddle.getText()) && buttonMiddle.getText().equals(buttonRight.getText())) {
                message.setText("Congratulations!!!\nYou win 1 free ticket\nWARNING: You cannot change your free ticket after quiting from the system");
                currentUser.setFreeTicket(currentUser.getFreeTicket() + 1);
            }
            else
                message.setText("We are sorry...\nYou win nothing");
        });
    }

}
