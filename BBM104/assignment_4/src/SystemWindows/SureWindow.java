package SystemWindows;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SureWindow {

    /**
     * Sure Window
     */
    public static void showWindow() {
        Stage sureStage = new Stage();
        sureStage.initModality(Modality.APPLICATION_MODAL); // this window must be dealed before making more changes in the system
        sureStage.setTitle("Closing the application will automatically save the progress");
        sureStage.getIcons().add(Window.logo);

        Scene sureScene;

        Label label = new Label("Are you sure you want to exit?");
        Button yesButton = new Button("Yes"),
                noButton = new Button("NO");
        HBox buttonHBox = new HBox();
        VBox sureVBox = new VBox();

        // Buttons
        yesButton.setOnAction(e -> {
            Window.closeEverything();
            sureStage.close();
            Window.stage.close();
        });

        noButton.setOnAction(e -> sureStage.close());

        // Cosmetics...
        label.setAlignment(Pos.CENTER);
        buttonHBox.setAlignment(Pos.CENTER);
        buttonHBox.setSpacing(20);
        sureVBox.setAlignment(Pos.CENTER);
        sureVBox.setSpacing(20);
        sureVBox.setPadding(new Insets(20));

        buttonHBox.getChildren().addAll(yesButton, noButton);

        sureVBox.getChildren().addAll(label, buttonHBox);

        sureScene = new Scene(sureVBox);

        sureStage.setScene(sureScene);
        sureStage.setWidth(640);
        sureStage.setResizable(false);
        sureStage.showAndWait();

    }

}
