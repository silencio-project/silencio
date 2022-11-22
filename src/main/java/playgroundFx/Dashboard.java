package playgroundFx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import playgroundFx.room_configurator.RoomConfigurator;


public class Dashboard extends Scene {


    public Dashboard(Stage stage) {
        super(getContent(stage), stage.getWidth(), stage.getHeight());
    }

    private static VBox getContent(Stage stage) {
        var room = new Button("Room");
        var configurator = new Button("Room Configurator");
        configurator.setOnAction(action -> {
            stage.setScene(new RoomConfigurator(stage));
        });
        var heading = new Label("Silencio.");
        heading.setPadding(new Insets(0, 0, 100, 0));
        var v = new VBox(heading, room, configurator);
        v.setAlignment(Pos.CENTER);
        return v;

    }
}
