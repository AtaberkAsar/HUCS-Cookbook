import javafx.application.Application;
import javafx.stage.Stage;

import CinemaReservationSystem.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        CinemaReservationSystem cinemaReservationSystem = new CinemaReservationSystem();
        cinemaReservationSystem.start(primaryStage);
    }
}
