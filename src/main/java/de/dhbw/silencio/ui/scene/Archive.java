package de.dhbw.silencio.ui.scene;

import de.dhbw.silencio.storage.Protocol;
import de.dhbw.silencio.storage.ProtocolRepositoryCsv;
import de.dhbw.silencio.storage.Room;
import de.dhbw.silencio.storage.RoomRepositoryCsv;
import de.dhbw.silencio.ui.components.RoomLayout;
import de.dhbw.silencio.ui.util.Typography;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


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
        super(new HBox(), stage.getWidth(), stage.getHeight(), stage, "Archive", new VBox());
        var layout = (HBox) this.getParentContent();

        List<Protocol> protocolList = List.of();
        List<Room> roomList = List.of();
        try {
            protocolList = new ProtocolRepositoryCsv().getAll();
            roomList = new RoomRepositoryCsv().getAll();
        } catch (IOException e) {
            System.out.println("Data storage request went wrong");
        }
        setCallback(() -> {
            if (timeline != null) timeline.stop();
        });

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

        List<Room> finalRoomList = roomList;
        listView.setOnMouseClicked(event -> {
            var selected = listView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                timeline.stop();
                isPlaying = false;
                play.setText("Play");
                currentIndex = 0;

                currentProtocol = selected;
                var room = getRoomByUid((ArrayList<Room>) finalRoomList, currentProtocol.getRoomUid());
                if (room != null) {
                    updateRoom(layout, room);
                    slider.setMax(currentProtocol.getData().length - 1);
                    slider.valueProperty().addListener((observable, oldValue, newValue) -> {
                        slider.setValue(newValue.intValue());
                        currentIndex = (int) slider.getValue();
                        roomLayout.updatePointer(currentProtocol.getData()[currentIndex][1], 200);
                        timestamp.setText(millisToLocalTime(currentProtocol.getData()[currentIndex][0]));
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

    private String millisToLocalTime(int m) {
        return Instant.ofEpochMilli(m).atZone(ZoneId.systemDefault()).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }


}
