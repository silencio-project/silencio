package de.dhbw.silencio.ui;

import de.dhbw.silencio.ui.scene.Dashboard;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */
public class App extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        stage.setHeight(800);
        stage.setWidth(1200);
        stage.setResizable(false);
        stage.setScene(new Dashboard(stage));
        stage.show();

    }

}
