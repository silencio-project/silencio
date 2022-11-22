package playgroundFx.components;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import playgroundFx.Dashboard;
public class DefaultScene extends Scene {

    public Stage stage;
    public Node content;

    public DefaultScene(Node content, double width, double height, Stage stage, String heading) {
        super(new VBox(getHeader(stage, heading), content), width, height);
        this.stage = stage;
        this.content = content;
    }

    private static AnchorPane getHeader(Stage stage, String title) {
        var home = new Button("Home");
        var heading = new Label(title);
        var logo = new Label("logo");
        var ap = new AnchorPane();

        heading.setFont(new Font("Arial", 30));
        ap.getChildren().addAll(home, heading, logo);
        AnchorPane.setTopAnchor(logo, 20d);
        AnchorPane.setRightAnchor(logo, 20d);
        AnchorPane.setTopAnchor(home, 20d);
        AnchorPane.setLeftAnchor(home, 20d);
        AnchorPane.setTopAnchor(heading, 20d);
        AnchorPane.setLeftAnchor(heading, stage.getWidth() / 2.25);

        home.setOnAction(action -> {
            stage.setScene(new Dashboard(stage));
        });
        ap.setPadding(new Insets(0,0,30,0));

        return ap;
    }

    public Node getParentContent() {
        return content;
    }

    /*public void updateScene*/
}
