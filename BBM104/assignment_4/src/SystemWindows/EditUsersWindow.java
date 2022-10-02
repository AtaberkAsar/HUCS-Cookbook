package SystemWindows;

import SystemDataTypes.*;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;

public class EditUsersWindow extends Window {

    public EditUsersWindow(int maxError, String title, double discountPercentage, int blockTime,
                           Stage stage, Image logo, MediaPlayer errorPlayer,
                           HashMap<String, User> users, HashMap<String, Film> films,
                           HashMap<HallKey, Hall> halls, HashMap<Hall, ArrayList<SeatRecord>> seatRecords,
                           User currentUser) {

        super(maxError, title, discountPercentage, blockTime,
              stage, logo, errorPlayer, users, films, halls, seatRecords, currentUser);

    }

    /**
     * Edit Users Window
     */
    protected static void showWindow() {

        Scene editUserScene;

        TableView<User> userTableView = new TableView<>();
        Button backButton = new Button("\u25C2 Back"),
                promoteDemoteClubMemberButton = new Button("Promote/Demote Club Member"),
                promoteDemoteAdminButton = new Button("Promote/Demote Admin");
        GridPane editUsersGridPane = new GridPane();

        //region Buttons

        // Adjusting Table View
        TableColumn<User, String> nameCol = new TableColumn<>("Username");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
        TableColumn<User, Boolean> clubMemberCol = new TableColumn<>("Club Member");
        clubMemberCol.setMinWidth(200);
        clubMemberCol.setCellValueFactory(new PropertyValueFactory<>("clubMemberStatus"));
        TableColumn<User, String> adminCol = new TableColumn<>("Admin");
        adminCol.setMinWidth(200);
        adminCol.setCellValueFactory(new PropertyValueFactory<>("adminStatus"));

        for(User user: users.values())
            if(!user.equals(currentUser))
                userTableView.getItems().add(user);

        backButton.setOnAction(e -> WelcomeWindow.showWindow());
        promoteDemoteClubMemberButton.setOnAction(e -> promoteDemoteClubMember(userTableView.getSelectionModel().getSelectedItem()));
        promoteDemoteAdminButton.setOnAction(e -> promoteDemoteAdmin(userTableView.getSelectionModel().getSelectedItem()));

        //endregion

        //region Cosmetics

        userTableView.getColumns().addAll(nameCol, clubMemberCol, adminCol);

        GridPane.setConstraints(userTableView, 0 ,0, 3, 3);
        GridPane.setHalignment(userTableView, HPos.CENTER);
        GridPane.setConstraints(backButton, 0, 3);
        GridPane.setHalignment(backButton, HPos.RIGHT);
        GridPane.setConstraints(promoteDemoteClubMemberButton, 1, 3);
        GridPane.setHalignment(promoteDemoteClubMemberButton, HPos.CENTER);
        GridPane.setConstraints(promoteDemoteAdminButton, 2, 3);

        editUsersGridPane.getChildren().addAll(userTableView, backButton, promoteDemoteClubMemberButton,
                promoteDemoteAdminButton);

        gridPaneAdjuster(editUsersGridPane, 4, 3);

        //endregion

        editUserScene = new Scene(editUsersGridPane);
        stage.setScene(editUserScene);
        stage.centerOnScreen();

    }


    /**
     * Promote or demote club member method
     * @param user User that will be promoted to club member or demoted from it
     */
    private static void promoteDemoteClubMember(User user) {
        user.setClubMemberStatus(!user.getClubMemberStatus());
        showWindow();
    }

    /**
     * Promote or demote admin method
     * @param user User that will be promoted to admin or demoted from it
     */
    private static void promoteDemoteAdmin(User user) {
        user.setAdminStatus(!user.getAdminStatus());
        showWindow();
    }

}
