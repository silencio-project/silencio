package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.scene.Dashboard;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        stage.setHeight(1000);
        stage.setWidth(1400);
        stage.setResizable(false);
        stage.setScene(new Dashboard(stage));
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
