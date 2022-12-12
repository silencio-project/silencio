package de.dhbw.silencio.ui.scene;

import de.dhbw.silencio.ui.util.Callback;
import de.dhbw.silencio.ui.util.Typography;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;


/**
 * @author Moritz Thoma
 * @since 1.0.0
 */
public abstract class DefaultScene extends Scene {

    public Stage stage;
    public Node content;
    private Callback callback;

    public DefaultScene(Node content, double width, double height, Stage stage, String title, VBox root) {
        super(root, width, height);
        this.stage = stage;
        this.content = content;

        var anchorPane = new AnchorPane();

        try (InputStream in = DefaultScene.class.getClassLoader().getResourceAsStream("logo_silencio_lightgrey.png")) {
            if (in == null) {
                throw new RuntimeException();
            }
            var logoImage = new Image(in);
            var logo = new ImageView(logoImage);
            logo.setFitHeight(70);
            logo.setFitWidth(70);
            anchorPane.getChildren().add(logo);
            AnchorPane.setTopAnchor(logo, 20d);
            AnchorPane.setRightAnchor(logo, 20d);
        } catch (IOException e) {
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

        home.setOnAction(action -> {
            if (callback != null) {
                callback.onBack();
            }
            stage.setScene(new Dashboard(stage));
        });
        anchorPane.setPadding(new Insets(0, 0, 10, 0));

        root.getChildren().addAll(anchorPane, content);

    }

    public Node getParentContent() {
        return content;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }


}
