package de.dhbw.silencio.ui.scene;


import de.dhbw.silencio.ui.components.RoomLayout;
import de.dhbw.silencio.ui.data.*;
import de.dhbw.silencio.ui.util.Typography;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.*;

import java.util.*;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class LiveView extends DefaultScene {

    private final int currentAngle = 0;
    private Room currentRoom;
    private RoomLayout roomLayout;
    private ArrayList<String> mic1List;
    private ArrayList<String> mic2List;
    private String microphone1 = "";
    private String microphone2 = "";

    public LiveView(Stage stage) {
        super(new HBox(), stage.getWidth(), stage.getHeight(), stage, "Live View");
        var layout = (HBox) this.getParentContent();

        var roomList = _TestData.rooms();
        mic1List = new ArrayList<>(List.of("mic1", "mic2", "mic3", "mic4", "mic5")); //TODO
        mic2List = new ArrayList<>(List.of("mic1", "mic2", "mic3", "mic4", "mic5")); //TODO

        currentRoom = Room.emptyRoom();
        roomLayout = new RoomLayout(currentRoom);

        Callback<ListView<Room>, ListCell<Room>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Room item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getDescription());
            }
        };

        var roomChoiceTitle = new Label("Your rooms: ");
        var mic1ChoiceTitle = new Label("Left microphone: ");
        var mic2ChoiceTitle = new Label("Right microphone: ");
        var labelInsets = new Insets(10, 10, 0, 0);
        roomChoiceTitle.setPadding(labelInsets);
        mic1ChoiceTitle.setPadding(labelInsets);
        mic2ChoiceTitle.setPadding(labelInsets);
        roomChoiceTitle.setFont(Typography.standardFont());
        mic1ChoiceTitle.setFont(Typography.standardFont());
        mic2ChoiceTitle.setFont(Typography.standardFont());

        var roomChoice = new ComboBox<Room>();
        roomChoice.setButtonCell(factory.call(null));
        roomChoice.setCellFactory(factory);
        roomChoice.setPrefWidth(200);
        roomChoice.getItems().addAll(roomList);
        roomChoice.valueProperty().addListener((observableValue, room, newRoom) -> {
                if (newRoom != null) {
                    currentRoom = newRoom;
                    updateRoom(layout, currentRoom);
                }
            }
        );

        var mic1Choice = new ComboBox<String>();
        var mic2Choice = new ComboBox<String>();

        mic1Choice.setPrefWidth(200);
        mic1Choice.getItems().addAll(mic1List);
        mic1Choice.valueProperty().addListener(((observableValue, old, newValue) -> {
            if (newValue != null) {
                microphone1 = newValue;
                mic2List = new ArrayList<>(List.of("mic1", "mic2", "mic3", "mic4", "mic5")); // TODO
                mic2List.remove(microphone1);
                mic2Choice.setItems(FXCollections.observableList(mic2List));

            }
        }));

        mic2Choice.setPrefWidth(200);
        mic2Choice.getItems().addAll(mic1List);
        mic2Choice.valueProperty().addListener(((observableValue, old, newValue) -> {
            if (newValue != null) {
                microphone2 = newValue;
                mic1List = new ArrayList<>(List.of("mic1", "mic2", "mic3", "mic4", "mic5")); // TODO
                mic1List.remove(microphone2);
                mic1Choice.setItems(FXCollections.observableList(mic1List));
            }
        }));


        var play = new Button("Start");
        var stop = new Button("Stop & Save");
        stop.setDisable(true);

        var break_ = new Button("Break");
        break_.setDisable(true);

        var controller = new HBox(play, break_);
        controller.setAlignment(Pos.TOP_CENTER);
        controller.setSpacing(20);

        var lastRecording = new Button("Last Recording");
        lastRecording.setOnAction(action -> stage.setScene(new Archive(stage)));

        var bottom = new VBox(controller, stop, lastRecording);
        bottom.setPadding(new Insets(500, 0, 0, 0));
        bottom.setAlignment(Pos.BOTTOM_CENTER);
        bottom.setFillWidth(true);
        bottom.setSpacing(20);

        var left = new VBox(roomChoiceTitle, roomChoice, mic1ChoiceTitle, mic1Choice, mic2ChoiceTitle, mic2Choice, bottom);
        left.setPadding(new Insets(0, 20, 0, 10));
        left.setSpacing(5);

        Timeline timeline = new Timeline(new KeyFrame(
            Duration.millis(250), ev -> roomLayout.updatePointer(currentAngle, 200))
        );
        timeline.setCycleCount(Animation.INDEFINITE);


        play.setOnAction(event -> {
            stop.setDisable(false);
            break_.setDisable(false);
            play.setDisable(true);
            lastRecording.setDisable(true);
        });
        break_.setOnAction(event -> {
            break_.setDisable(true);
            stop.setDisable(true);
            play.setDisable(false);
        });
        stop.setOnAction(event -> {
            stop.setDisable(true);
            break_.setDisable(true);
            play.setDisable(false);
            lastRecording.setDisable(false);
        });

        layout.getChildren().addAll(left, roomLayout);
    }

    private void updateRoom(HBox layout, Room room) {
        for (var child : layout.getChildren()) {
            if (child instanceof RoomLayout) {
                layout.getChildren().remove(child);
                roomLayout = new RoomLayout(room);
                layout.getChildren().add(roomLayout);
                break;
            }
        }
    }
}
