package de.dhbw.silencio.ui.scene;

import de.dhbw.silencio.storage.*;
import de.dhbw.silencio.ui.components.RoomLayout;
import de.dhbw.silencio.ui.data._TestData;
import de.dhbw.silencio.ui.util.Typography;
import javafx.animation.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class Archive extends DefaultScene {

    private int currentIndex = 0;
    private Protocol currentProtocol;
    private Timeline timeline;
    private boolean isPlaying = false;
    private RoomLayout roomLayout;

    public Archive(Stage stage) {
        super(new HBox(), stage.getWidth(), stage.getHeight(), stage, "Archive");
        var layout = (HBox) this.getParentContent();

        var protocolList = _TestData.protocols();
        var roomList = _TestData.rooms();

        var currentRoom = Room.emptyRoom();
        roomLayout = new RoomLayout(currentRoom);

        var listTitle = new Label("Protocols");
        listTitle.setFont(Typography.standardFont());

        Callback<ListView<Protocol>, ListCell<Protocol>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Protocol item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getTime().format(DateTimeFormatter.ofPattern("dd.MM.yyyy - HH:mm")));
            }
        };

        var listView = new ListView<Protocol>();
        listView.setMaxWidth(210);
        listView.getItems().addAll(protocolList);
        listView.setCellFactory(factory);

        var play = new Button("Play");
        play.setDisable(true);

        var timestamp = new Label("");
        timestamp.setFont(Typography.timestampFont());

        var slider = new Slider();
        slider.setBlockIncrement(1);
        slider.setMin(0);
        slider.setDisable(true);

        var player = new VBox(play, slider, timestamp);
        player.setSpacing(20);
        player.setAlignment(Pos.TOP_CENTER);

        var left = new VBox(listTitle, listView, player);
        left.setPadding(new Insets(0, 10, 0, 10));
        left.setSpacing(25);

        listView.setOnMouseClicked(event -> {
            var selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                timeline.stop();
                isPlaying = false;
                play.setText("Play");
                currentIndex = 0;

                currentProtocol = selected;
                var room = getRoomByUid(roomList, currentProtocol.getRoomUid());
                if (room != null) {
                    updateRoom(layout, room);
                    slider.setMax(currentProtocol.getData().length - 1);
                    slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                        slider.setValue(newValue.intValue());
                        currentIndex = (int) slider.getValue();
                        roomLayout.updatePointer(currentProtocol.getData()[currentIndex][1], 200);
                        timestamp.setText(String.valueOf(currentProtocol.getData()[currentIndex][0]));
                    });
                    slider.setDisable(false);
                    play.setDisable(false);
                } else {
                    System.out.println("Room not available");
                    // TODO Alert
                }
            } else {
                slider.setDisable(true);
                play.setDisable(true);
            }
        });

        timeline = new Timeline(new KeyFrame(Duration.millis(250), ev -> {
            if (currentProtocol.getData().length > currentIndex) {
                slider.setValue(currentIndex);
                roomLayout.updatePointer(currentProtocol.getData()[currentIndex][1], 200);
                timestamp.setText(String.valueOf(currentProtocol.getData()[currentIndex][0]));
                currentIndex++;
            } else {
                timeline.stop();
                isPlaying = false;
                play.setText("Play");
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        play.setOnAction((e) -> {
            if (!isPlaying) {
                if (currentIndex == currentProtocol.getData().length) {
                    currentIndex = 0;
                }
                timeline.play();
                isPlaying = true;
                play.setText("Break");
            } else {
                timeline.stop();
                isPlaying = false;
                play.setText("Play");
            }
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

    private Room getRoomByUid(ArrayList<Room> list, int uid) {
        return list.stream()
            .filter((room) -> room.getUid() == uid)
            .findAny()
            .orElse(null);
    }


}
