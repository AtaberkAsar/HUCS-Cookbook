package SystemWindows;

import CinemaReservationSystem.Utils;
import SystemDataTypes.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
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
import java.util.Arrays;
import java.util.HashMap;

public class HallWindow extends Window {

    public HallWindow(int maxError, String title, double discountPercentage, int blockTime,
                      Stage stage, Image logo, MediaPlayer errorPlayer,
                      HashMap<String, User> users, HashMap<String, Film> films,
                      HashMap<HallKey, Hall> halls, HashMap<Hall, ArrayList<SeatRecord>> seatRecords,
                      User currentUser) {

        super(maxError, title, discountPercentage, blockTime,
                stage, logo, errorPlayer, users, films, halls, seatRecords, currentUser);

    }


    /**
     * Client Hall Window
     * @param hallName Hall name
     * @param film Film
     * @param message Message that will be displayed at the bottom of the client hall window
     */
    protected static void showClientHall(String hallName, Film film, Label message){

        Hall hall = halls.get(new HallKey(film, hallName));
        if(hall == null) {
            System.err.println("No more hall left for the specified film in the system, or existing halls cannot be found!");
            return;
        }

        ArrayList<SeatRecord> seats = seatRecords.get(hall);

        Scene hallScene;

        Label filmLabel = new Label(film.getFilmName() + "(" + film.getDuration() + ") Hall: " + hall.getHallName());
        GridPane clientHallGridPane = new GridPane();
        Button backButton = new Button("Back");
        Image emptySeat = new Image(Utils.emptySeatImagePath),
                reservedSeat = new Image(Utils.reservedSeatPath);

        //region Button

        // adding seats to grid pane
        for(SeatRecord seat: seats) {
            int i = seat.getRowOfSeat();
            int j = seat.getColumnOfSeat();
            Button tempButton = new Button();
            tempButton.setPadding(Insets.EMPTY);
            if(seat.getUser() == null) { // if an empty seat
                tempButton.setGraphic(addSeat(emptySeat));
                tempButton.setOnAction(e -> emptySeatChoose(currentUser, message, i, j, hall));
            }
            else if(seat.getUser().equals(currentUser)) { // if user's reserved seat
                tempButton.setGraphic(addSeat(reservedSeat));
                tempButton.setOnAction(e -> reservedSeatChoose(currentUser, message, i, j, hall));
            }
            else { // if someone else's reserved seat
                ImageView tempImageView = addSeat(reservedSeat);
                tempImageView.setOpacity(0.25);
                tempButton.setGraphic(tempImageView);
                tempButton.setDisable(true);
            }
            GridPane.setConstraints(tempButton, j, i + 1);
            clientHallGridPane.getChildren().add(tempButton);
        }

        backButton.setOnAction(e -> FilmWindow.showWindow(hall.getFilm().getFilmName()));
        backButton.setMinSize(70, 50);

        //endregion

        //region Cosmetics

        filmLabel.setTextAlignment(TextAlignment.CENTER);

        GridPane.setConstraints(filmLabel, 0, 0, hall.getColumn(), 1);
        GridPane.setHalignment(filmLabel, HPos.CENTER);
        GridPane.setConstraints(message, 0, hall.getRow() + 1, hall.getColumn(), 1);
        GridPane.setHalignment(message, HPos.CENTER);
        GridPane.setConstraints(backButton, 0, hall.getRow() + 2);

        clientHallGridPane.getChildren().addAll(filmLabel, backButton, message);
        hallScene = new Scene(clientHallGridPane);

        gridPaneAdjuster(clientHallGridPane, hall.getRow() + 3, hall.getColumn());

        //endregion

        stage.setScene(hallScene);
        stage.centerOnScreen();

    }

    /**
     * Admin Hall Window
     * @param hallName Hall name
     * @param film Film
     * @param message Message that will be displayed at the bottom of the admin hall window
     * @param tempComboBoxVal default value that would be chosen at the combo box
     */
    protected static void showAdminHall(String hallName, Film film, Label message, String tempComboBoxVal){

        Hall hall = halls.get(new HallKey(film, hallName));
        if(hall == null) {
            System.err.println("No more hall left for the specified film in the system, or existing halls cannot be found!");
            return;
        }

        ArrayList<SeatRecord> seats = seatRecords.get(hall);

        Scene hallScene;

        Label filmLabel = new Label(film.getFilmName() + "(" + film.getDuration() + ") Hall: " + hall.getHallName()),
                hoverMessage = new Label();

        GridPane adminHallGridPane = new GridPane();
        Button backButton = new Button("Back");
        Image emptySeat = new Image(Utils.emptySeatImagePath),
                reservedSeat = new Image(Utils.reservedSeatPath);
        ComboBox<String> userComboBox = new ComboBox<>();

        //region Buttons

        // adding users to combo box
        for(String tempUser : users.keySet()) {
            userComboBox.getItems().add(tempUser);
        }
        userComboBox.setValue(tempComboBoxVal);

        for(SeatRecord seat: seats) {
            int i = seat.getRowOfSeat();
            int j = seat.getColumnOfSeat();
            User tempUser = seat.getUser();
            Button tempButton = new Button();
            tempButton.setPadding(Insets.EMPTY);

            if(tempUser == null) {
                tempButton.setGraphic(addSeat(emptySeat));
                tempButton.hoverProperty().addListener((v, oldValue, newValue) -> {
                    if(newValue)
                        hoverMessage.setText("Not bought yet!");
                    else
                        hoverMessage.setText("");
                });
                tempButton.setOnAction(e -> emptySeatChoose(users.get(userComboBox.getValue()), message, i, j, hall));
            }

            else {
                tempButton.setGraphic(addSeat(reservedSeat));
                tempButton.hoverProperty().addListener((v, oldValue, newValue) -> {
                    if(newValue)
                        hoverMessage.setText("Bought by " + tempUser.getUserName() + " for " + seat.getPriceItHasBeenBought() + " TL!");
                    else
                        hoverMessage.setText("");
                });
                tempButton.setOnAction(e -> reservedSeatChoose(tempUser, message, i, j, hall));
            }

            GridPane.setConstraints(tempButton, j, i + 1);
            adminHallGridPane.getChildren().add(tempButton);
        }

        backButton.setOnAction(e -> FilmWindow.showWindow(hall.getFilm().getFilmName()));
        backButton.setMinSize(70, 50);

        //endregion

        //region Cosmetics

        filmLabel.setTextAlignment(TextAlignment.CENTER);

        GridPane.setConstraints(filmLabel, 0, 0, hall.getColumn(), 1);
        GridPane.setHalignment(filmLabel, HPos.CENTER);
        GridPane.setConstraints(hoverMessage, 0, hall.getRow() + 1, hall.getColumn(), 1);
        GridPane.setHalignment(hoverMessage, HPos.CENTER);
        GridPane.setConstraints(message, 0, hall.getRow() + 2, hall.getColumn(), 1);
        GridPane.setHalignment(message, HPos.CENTER);
        GridPane.setConstraints(userComboBox, 0, hall.getRow() + 3, hall.getColumn(), 1);
        GridPane.setHalignment(userComboBox, HPos.CENTER);
        GridPane.setConstraints(backButton, 0, hall.getRow() + 4);

        adminHallGridPane.getChildren().addAll(filmLabel, hoverMessage, message, userComboBox, backButton);

        gridPaneAdjuster(adminHallGridPane, hall.getRow() + 5, hall.getColumn());
        userComboBox.setMaxWidth(300);

        //endregion

        hallScene = new Scene(adminHallGridPane);


        stage.setScene(hallScene);
        stage.centerOnScreen();

    }

    /**
     * Empty seat choose method
     * @param currentUser Current user
     * @param message Message that will be displayed at the end of hall window
     * @param row Row
     * @param column Col
     * @param currentHall Current hall
     */
    private static void emptySeatChoose(User currentUser, Label message, int row, int column, Hall currentHall) {

        int priceBought;
        if (currentUser.getFreeTicket() > 0) {
            priceBought = 0;
            currentUser.setFreeTicket(currentUser.getFreeTicket() - 1);
            currentUser.getFreeSeats().add(new Integer[] {row, column});
        }
        else {
            priceBought = (int) Math.round(currentUser.getClubMemberStatus() ?
                    (currentHall.getPricePerSeat()*((100 - discountPercentage) / 100.0)) : currentHall.getPricePerSeat());
            currentUser.setTicketCounter(currentUser.getTicketCounter() + 1);
        }

        if(Window.currentUser.getAdminStatus())
            message.setText("Seat at " + (row + 1) + "-" + (column + 1) + " is bought for " + currentUser.getUserName()
                    + " for "+ priceBought + " TL successfully!");
        else
            message.setText("Seat at " + (row + 1) + "-" + (column + 1) + " is bought for " + priceBought + " TL successfully!");

        ArrayList<SeatRecord> seatBought = seatRecords.get(currentHall);

        for(SeatRecord seat: seatBought) {
            if(seat.getRowOfSeat() == row && seat.getColumnOfSeat() == column) {
                seat.setUser(currentUser);
                seat.setPriceItHasBeenBought(priceBought);
                break;
            }
        }
        seatRecords.put(currentHall, seatBought);

        if(Window.currentUser.getAdminStatus())
            showAdminHall(currentHall.getHallName(), currentHall.getFilm(), message, currentUser.getUserName());
        else
            showClientHall(currentHall.getHallName(), currentHall.getFilm(), message);

    }

    /**
     * Reserved seat choose method
     * @param currentUser Current user
     * @param message Message that will be displayed at the bottom of hall window
     * @param row Row
     * @param column Col
     * @param currentHall Current hall
     */
    private static void reservedSeatChoose(User currentUser, Label message, int row, int column, Hall currentHall) {

        if(Window.currentUser.getAdminStatus())
            message.setText("Seat at " + (row + 1) + "-" + (column + 1) + " is refunded to " + currentUser.getUserName() +
                    " successfully!");
        else
            message.setText("Seat at " + (row + 1) + "-" + (column + 1) + " is refunded successfully!");

        ArrayList<SeatRecord> seatRefunded = seatRecords.get(currentHall);

        for(Integer[] freeSeat: currentUser.getFreeSeats()) {
            if(Arrays.equals(freeSeat, new Integer[]{row, column}))
                currentUser.setFreeTicket(currentUser.getFreeTicket() + 1);
        }

        currentUser.setTicketCounter(currentUser.getTicketCounter() - 1);

        for(SeatRecord seat: seatRefunded) {
            if(seat.getColumnOfSeat() == column && seat.getRowOfSeat() == row) {
                seat.setUser(null);
                seat.setPriceItHasBeenBought(0);
                break;
            }
        }

        seatRecords.put(currentHall, seatRefunded);

        if(Window.currentUser.getAdminStatus())
            showAdminHall(currentHall.getHallName(), currentHall.getFilm(), message, currentUser.getUserName());
        else
            showClientHall(currentHall.getHallName(), currentHall.getFilm(), message);

    }

    /**
     * Method that adds seat Image Views
     * @param seatImage Seat Image
     * @return Image View of given Image
     */
    private static ImageView addSeat(Image seatImage) {
        ImageView tempImageView = new ImageView(seatImage);
        tempImageView.setFitWidth(50);
        tempImageView.setFitHeight(50);
        return tempImageView;
    }


}
