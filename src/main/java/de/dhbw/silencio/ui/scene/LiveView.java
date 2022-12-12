package de.dhbw.silencio.ui.scene;


import de.dhbw.silencio.audio.DirectionProviderBuilder;
import de.dhbw.silencio.audio.Microphone;
import de.dhbw.silencio.storage.Protocol;
import de.dhbw.silencio.storage.Room;
import de.dhbw.silencio.storage.RoomRepositoryCsv;
import de.dhbw.silencio.ui.components.RoomLayout;
import de.dhbw.silencio.ui.util.Typography;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class LiveView extends DefaultScene {

    private double currentAngle = 0;
    private Room currentRoom;
    private RoomLayout roomLayout;
    private List<String> mic1List;
    private List<String> mic2List;
    private String microphone1 = "";
    private String microphone2 = "";
    private Timeline timeline;
    private Protocol currentProtocol;
    private boolean isRecording;

    private DirectionProviderBuilder directionProviderBuilder;

    public LiveView(Stage stage) {
        super(new HBox(), stage.getWidth(), stage.getHeight(), stage, "Live View", new VBox());
        var layout = (HBox) this.getParentContent();

        List<Room> roomList = List.of();
        try {
            roomList = new RoomRepositoryCsv().getAll();
        } catch (IOException e) {
            System.out.println("Data storage request went wrong");
        }
        mic1List = new ArrayList<>(Microphone.getAvailableDevices());
        mic2List = new ArrayList<>(Microphone.getAvailableDevices());

        currentRoom = Room.emptyRoom();
        roomLayout = new RoomLayout(currentRoom);

        Callback<ListView<Room>, ListCell<Room>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Room item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getDescription());
            }
        };

        setCallback(() -> {
            if (timeline != null) timeline.stop();
        });

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
                    startTimeLine(roomChoice);
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
                mic2List = new ArrayList<>(Microphone.getAvailableDevices());
                mic2List.remove(microphone1);
                mic2Choice.setItems(FXCollections.observableList(mic2List));

                startTimeLine(roomChoice);
            }
        }));

        mic2Choice.setPrefWidth(200);
        mic2Choice.getItems().addAll(mic1List);
        mic2Choice.valueProperty().addListener(((observableValue, old, newValue) -> {
            if (newValue != null) {
                microphone2 = newValue;
                mic1List = new ArrayList<>(Microphone.getAvailableDevices());
                mic1List.remove(microphone2);
                mic1Choice.setItems(FXCollections.observableList(mic1List));

                startTimeLine(roomChoice);
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

        currentProtocol = new Protocol();

        play.setOnAction(event -> {
            stop.setDisable(false);
            break_.setDisable(false);
            play.setDisable(true);
            lastRecording.setDisable(true);
            isRecording = true;
            if (currentProtocol.getTime() == null) {
                currentProtocol.setTime(LocalDateTime.now());
            }
            currentProtocol.setRoomUid(currentRoom.getUid());
        });
        break_.setOnAction(event -> {
            break_.setDisable(true);
            stop.setDisable(true);
            play.setDisable(false);
            isRecording = false;
        });
        stop.setOnAction(event -> {
            stop.setDisable(true);
            break_.setDisable(true);
            play.setDisable(false);
            lastRecording.setDisable(false);
            isRecording = false;
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

    private boolean isValid(Protocol protocol) {
        return protocol.getData().length > 0 && protocol.getTime() != null && protocol.getRoomUid() != 0;
    }


    private void startTimeLine(ComboBox<Room> roomChoice) {
        if (!microphone1.isEmpty() && !microphone2.isEmpty() && !roomChoice.getSelectionModel().isEmpty()) {
            new Thread(new DirectionProviderBuilder()
                .deviceName1(microphone1)
                .deviceName2(microphone2)
                .callback(angle -> currentAngle = angle)
                .build()).start();

            timeline = new Timeline(new KeyFrame(
                Duration.millis(100), ev -> {
                roomLayout.updatePointer(currentAngle, 200);

                if (isRecording) {
                    if (currentProtocol.getData() == null) {
                        currentProtocol.setData(new int[360000][2]); // max 10h
                    }
                    currentProtocol.getData()[currentProtocol.getData().length - 1][0] = (int) System.currentTimeMillis();
                    currentProtocol.getData()[currentProtocol.getData().length - 1][1] = (int) currentAngle; // TODO should be double
                }

            }));
            this.setCallback(() -> timeline.stop());
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
    }
}
