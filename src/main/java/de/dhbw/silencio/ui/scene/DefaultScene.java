package de.dhbw.silencio.ui.scene;

import de.dhbw.silencio.ui.util.Typography;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */
public class DefaultScene extends Scene {

    public Stage stage;
    public Node content;

    public DefaultScene(Node content, double width, double height, Stage stage, String heading) {
        super(new VBox(getHeader(stage, heading), content), width, height);
        this.stage = stage;
        this.content = content;
    }

    private static AnchorPane getHeader(Stage stage, String title) {
        var anchorPane = new AnchorPane();
        try {
            var logoImage = new Image(new FileInputStream("src/main/resources/logo_silencio_lightgrey.png"));
            var logo = new ImageView(logoImage);
            logo.setFitHeight(70);
            logo.setFitWidth(70);
            anchorPane.getChildren().add(logo);
            AnchorPane.setTopAnchor(logo, 20d);
            AnchorPane.setRightAnchor(logo, 20d);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        var home = new Button("Home");
        var heading = new Label(title);

        heading.setFont(Typography.sceneHeadingFont());
        anchorPane.getChildren().addAll(home, heading);
        AnchorPane.setTopAnchor(home, 20d);
        AnchorPane.setLeftAnchor(home, 20d);
        AnchorPane.setTopAnchor(heading, 20d);
        AnchorPane.setLeftAnchor(heading, stage.getWidth() / 2.25);

        home.setOnAction(action -> stage.setScene(new Dashboard(stage)));
        anchorPane.setPadding(new Insets(0, 0, 10, 0));

        return anchorPane;
    }

    public Node getParentContent() {
        return content;
    }

    /*public void updateScene*/
}
