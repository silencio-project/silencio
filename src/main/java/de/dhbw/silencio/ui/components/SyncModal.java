package de.dhbw.silencio.ui.components;

import javafx.animation.*;
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.util.Duration;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class SyncModal {

    private int timeSeconds = 10;

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
}
