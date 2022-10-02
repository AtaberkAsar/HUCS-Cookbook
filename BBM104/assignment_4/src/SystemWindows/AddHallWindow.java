package SystemWindows;

import SystemDataTypes.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class AddHallWindow extends Window {

    public AddHallWindow(int maxError, String title, double discountPercentage, int blockTime,
                         Stage stage, Image logo, MediaPlayer errorPlayer,
                         HashMap<String, User> users, HashMap<String, Film> films,
                         HashMap<HallKey, Hall> halls, HashMap<Hall, ArrayList<SeatRecord>> seatRecords,
                         User currentUser) {

        super(maxError, title, discountPercentage, blockTime,
              stage, logo, errorPlayer, users, films, halls, seatRecords, currentUser);

    }


    /**
     * Add Hall Window
     * @param film Film which the hall will be added for
     */
    protected static void showWindow(Film film) {
        Scene addHallScene;

        Label filmLabel = new Label(film.getFilmName() + " (" + film.getDuration() + " minutes)"),
                row = new Label("Row:"),
                column = new Label("Column:"),
                name = new Label("Name:"),
                price = new Label("Price"),
                message = new Label();
        ComboBox<Integer> rowComboBox = new ComboBox<>(),
                colComboBox = new ComboBox<>();
        TextField nameField = new TextField(),
                priceField = new TextField();
        Button backButton = new Button("\u25C2 Back"),
                okButton = new Button("OK");
        GridPane addHallGridPane = new GridPane();

        //region Buttons

        // adding  rows and cols to combo box
        for(int i = 3; i < 11; i++) {
            colComboBox.getItems().add(i);
            rowComboBox.getItems().add(i);
        }
        colComboBox.setValue(3);
        rowComboBox.setValue(3);

        backButton.setOnAction(e -> FilmWindow.showWindow(film.getFilmName()));
        okButton.setOnAction(e -> addHall(film, nameField, rowComboBox.getValue(), colComboBox.getValue(), priceField, message));

        //endregion

        //region Cosmetics

        filmLabel.setTextAlignment(TextAlignment.CENTER);
        message.setTextAlignment(TextAlignment.CENTER);

        addHallGridPane.setHgap(20);
        addHallGridPane.setVgap(20);
        addHallGridPane.setAlignment(Pos.CENTER);
        addHallGridPane.setPadding(new Insets(20));

        GridPane.setConstraints(filmLabel, 0, 0, 2, 1);
        GridPane.setHalignment(filmLabel, HPos.CENTER);
        GridPane.setConstraints(row, 0, 1);
        GridPane.setConstraints(column, 0, 2);
        GridPane.setConstraints(rowComboBox, 1, 1);
        GridPane.setConstraints(colComboBox, 1, 2);
        GridPane.setConstraints(name, 0, 3);
        GridPane.setConstraints(price, 0, 4);
        GridPane.setConstraints(nameField, 1, 3);
        GridPane.setConstraints(priceField, 1, 4);
        GridPane.setConstraints(backButton, 0, 5);
        GridPane.setConstraints(okButton, 1, 5);
        GridPane.setHalignment(okButton, HPos.RIGHT);
        GridPane.setConstraints(message, 0, 6, 2, 1);
        GridPane.setHalignment(message, HPos.CENTER);

        addHallGridPane.getChildren().addAll(filmLabel, row, column, rowComboBox, colComboBox,
                name, price, nameField, priceField, backButton, okButton, message);

        gridPaneAdjuster(addHallGridPane, 7, 2);

        rowComboBox.setMaxWidth(100);
        colComboBox.setMaxWidth(100);

        //endregion

        addHallScene = new Scene(addHallGridPane);

        stage.setScene(addHallScene);
        stage.centerOnScreen();

    }


    /**
     * New hall addition method
     * @param film Film which the hall will be added for
     * @param hallNameField Hall name
     * @param row Row number
     * @param col Column number
     * @param priceField Price per seat
     * @param message Message that will be displayed on the bottom of addHallWindow
     */
    private static void addHall(Film film, TextField hallNameField, int row, int col, TextField priceField, Label message) {

        boolean flag = false;

        String hallName = hallNameField.getText(),
               price = priceField.getText();

        HallKey hallKey = new HallKey(film, hallName);
        int intPrice = 0;
        try {
            intPrice = Integer.parseInt(price);
            if(intPrice <= 0)
                throw new NumberFormatException();
        } catch (NumberFormatException numberFormatException) {
            flag = true;
        }

        if(hallName.equals("")) { // if hall name is empty
            message.setText("ERROR: Hall name could not be empty!");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            return;
        }
        if(price.equals("")) { // if price is empty
            message.setText("ERROR: Price could not be empty!");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            return;
        }
        else if(flag) { // if price is not integer
            message.setText("ERROR: Price has to be a positive integer!");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            return;
        }
        else if(halls.containsKey(hallKey)) { // if hall already exists
            message.setText("ERROR: Hall already exists");
            errorPlayer.seek(errorPlayer.getStartTime());
            errorPlayer.play();
            return;
        }

        Hall newHall = new Hall(film, hallName, intPrice, row, col);

        ArrayList<SeatRecord> seats = new ArrayList<>();
        for(int i = 0; i < row; i++)
            for(int j = 0; j < col; j++){
                seats.add(new SeatRecord(film, newHall, i, j, null, 0));
            }

        halls.put(hallKey, newHall);
        seatRecords.put(newHall, seats);
        message.setText("Success: Hall successfully created!");

        hallNameField.clear();
        priceField.clear();
    }

}
