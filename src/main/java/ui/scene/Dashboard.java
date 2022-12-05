package ui.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ui.util.Typography;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class Dashboard extends Scene {


    public Dashboard(Stage stage) {
        super(getContent(stage), stage.getWidth(), stage.getHeight());
    }

    private static VBox getContent(Stage stage) {
        var heading = new Label("Silencio.");
        heading.setFont(Typography.titleFont());
        heading.setPadding(new Insets(0, 0, 100, 0));
        var room = new Button("Room");
        room.setMinWidth(200);
        var archive = new Button("Archive");
        archive.setMinWidth(200);
        var configurator = new Button("Room Configurator");
        configurator.setMinWidth(200);

        room.setOnAction(action -> stage.setScene(new LiveView(stage)));
        archive.setOnAction(action -> stage.setScene(new Archive(stage)));
        configurator.setOnAction(action -> stage.setScene(new RoomConfigurator(stage)));

        var v = new VBox(heading, room, archive, configurator);
        v.setAlignment(Pos.CENTER);
        v.setSpacing(20);
        return v;

    }
}
