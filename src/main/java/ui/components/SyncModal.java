package ui.components;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class SyncModal {

    private int timeSeconds = 10;

    public boolean display() {
        var stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        var layout = new VBox();
        layout.setAlignment(Pos.BASELINE_CENTER);
        layout.setSpacing(20);

        Label counterLabel = new Label("10");
        Label state = new Label("Sync required");

        var start = new Button("Start");
        start.setOnAction(e -> {

            counterLabel.setText(String.valueOf(timeSeconds));
            var timeline = new Timeline();
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        timeSeconds--;
                        counterLabel.setText(String.valueOf(timeSeconds));
                        if (timeSeconds <= 0) {
                            timeline.stop();
                        }
                    }
                }));
            timeline.playFromStart();


        });
        var close = new Button("Close");
        close.setOnAction(e -> {
            stage.close();
        });
        var buttons = new HBox(start, close);
        buttons.setSpacing(20);
        buttons.setAlignment(Pos.BASELINE_CENTER);

        layout.getChildren().addAll(counterLabel, state, buttons);
        Scene scene = new Scene(layout, 250, 150);
        stage.setTitle("Dialog");
        stage.setScene(scene);
        stage.showAndWait();

        return true;
    }

    private static void startSync(Label label) {
        for (int i = 4; i > 0; i--) {
            try {
                Thread.sleep(1000);
                label.setText(String.valueOf(i));

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
