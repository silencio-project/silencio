package de.dhbw.silencio.ui.scene;

import de.dhbw.silencio.storage.Room;
import de.dhbw.silencio.storage.RoomRepositoryCsv;
import de.dhbw.silencio.ui.components.LabeledDoubleNumberField;
import de.dhbw.silencio.ui.components.LabeledNumberField;
import de.dhbw.silencio.ui.components.LabeledTextField;
import de.dhbw.silencio.ui.components.RoomLayout;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class RoomConfigurator extends DefaultScene {

    private Room currentSelected;
    private RoomLayout roomLayout;
    private List<Room> roomList = List.of();

    public RoomConfigurator(Stage stage) {
        super(new HBox(), stage.getWidth(), stage.getHeight(), stage, "RoomConfigurator", new VBox());
        var layout = (HBox) this.getParentContent();
        var grid = new GridPane();
        var roomRepo = new RoomRepositoryCsv();

        try {
            roomList = roomRepo.getAll();
        } catch (IOException e) {
            System.out.println("Data storage request went wrong");
        }

        var description = new LabeledTextField("Description");
        var roomSize = new LabeledDoubleNumberField("Room Size (length x width) *");
        var desk = new LabeledNumberField("Desk: Distance to the board *");
        var distanceDeskToWallLeft = new LabeledNumberField("Desk :Distance to wall left *");
        var firstRow = new LabeledNumberField("First Row: Distance to the desk *");
        var tableSize = new LabeledDoubleNumberField("Table Size (length x width) *");
        var amountRows = new LabeledNumberField("Amount Rows");
        var amountTablesPerRow = new LabeledNumberField("Amount tables per row");
        var distanceBetweenRows = new LabeledNumberField("Distance between rows *");
        var distanceToWallLeft = new LabeledNumberField("Distance to wall left *");
        var cm = new Label("* in cm ");
        cm.setPadding(new Insets(0, 0, 10, 10));


        var save = new Button("Save");
        save.setDisable(true);

        var refresh = new Button("Refresh");
        refresh.setOnAction(actionEvent -> {
            int dx = 0;
            int dy = 0;
            int deskX = 0;
            if (roomSize.getValue2() < distanceToWallLeft.getValue() + (amountTablesPerRow.getValue() * tableSize.getValue2())) {
                dx = (distanceToWallLeft.getValue() + (amountTablesPerRow.getValue() * tableSize.getValue2())) - roomSize.getValue2();
            }
            if (roomSize.getValue1() < desk.getValue() + tableSize.getValue1() + firstRow.getValue() + (amountRows.getValue() * tableSize.getValue1()) + (distanceBetweenRows.getValue() * (amountRows.getValue() - 1))) {
                dy = (desk.getValue() + tableSize.getValue1() + firstRow.getValue() + (amountRows.getValue() * tableSize.getValue1()) + (distanceBetweenRows.getValue() * (amountRows.getValue() - 1))) - roomSize.getValue1();
            }
            if (roomSize.getValue2() < tableSize.getValue2() * 2 + distanceDeskToWallLeft.getValue()) {
                deskX = tableSize.getValue2() * 2 + distanceDeskToWallLeft.getValue() - roomSize.getValue2();
            }

            if (dx != 0 || dy != 0 || deskX != 0 || description.getText().isEmpty()) {
                var alert = new Alert(Alert.AlertType.WARNING);
                String alertTextX = dx == 0 ? "" : "Your current room configuration exceeds the specified room width of " + roomSize.getValue2() + " cm by " + dx + " cm \n";
                String alertTextY = dy == 0 ? "" : "Your current room configuration exceeds the specified room length of " + roomSize.getValue1() + " cm by " + dy + " cm \n";
                String alertTextDeskX = deskX == 0 ? "" : "Your current desk position exceeds the specified room width of " + roomSize.getValue2() + " cm by " + deskX + " cm \n";
                String alertDes = !description.getText().isEmpty() ? "" : "Description is required";
                alert.setContentText(alertTextX + alertTextY + alertTextDeskX + alertDes);
                alert.setHeaderText("Please revise your specifications!");
                alert.show();

            } else {
                currentSelected.setLength(roomSize.getValue1());
                currentSelected.setWidth(roomSize.getValue2());
                currentSelected.setDistanceDeskToBoard(desk.getValue());
                currentSelected.setDistanceFirstRowToDesk(firstRow.getValue());
                currentSelected.setTableLength(tableSize.getValue1());
                currentSelected.setTableWidth(tableSize.getValue2());
                currentSelected.setRows(amountRows.getValue());
                currentSelected.setTablesPerRow(amountTablesPerRow.getValue());
                currentSelected.setBetweenRows(distanceBetweenRows.getValue());
                currentSelected.setDeskDistanceToWallLeft(distanceDeskToWallLeft.getValue());
                currentSelected.setDistanceToWallLeft(distanceToWallLeft.getValue());
                currentSelected.setDescription(description.getText());
                updateRoom(layout, currentSelected);
                save.setDisable(false);
            }
        });


        var newRoom = new Button("New");

        var hButtons = new HBox(newRoom, refresh, save);
        hButtons.setAlignment(Pos.BASELINE_CENTER);
        hButtons.setSpacing(10);

        grid.add(description, 0, 0);
        grid.add(roomSize, 0, 1);
        grid.add(desk, 0, 2);
        grid.add(distanceDeskToWallLeft, 0, 3);
        grid.add(tableSize, 0, 4);
        grid.add(amountRows, 0, 5);
        grid.add(amountTablesPerRow, 0, 6);
        grid.add(distanceBetweenRows, 0, 7);
        grid.add(firstRow, 0, 8);
        grid.add(distanceToWallLeft, 0, 9);
        grid.add(cm, 0, 10);
        grid.add(hButtons, 0, 11);


        var dropDown = new ComboBox<Room>();
        Callback<ListView<Room>, ListCell<Room>> factory = lv -> new ListCell<>() {
            @Override
            protected void updateItem(Room item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getDescription());
            }
        };
        dropDown.setButtonCell(factory.call(null));
        dropDown.setCellFactory(factory);
        dropDown.setPrefWidth(200);
        dropDown.getItems().addAll(roomList);
        dropDown.getSelectionModel().selectFirst();

        dropDown.valueProperty().addListener((observableValue, room1, t1) -> {
                if (t1 != null) {
                    System.out.println(t1.getDescription());
                    currentSelected = t1;
                    updateFields(currentSelected, description, roomSize, desk, firstRow, tableSize, amountRows, amountTablesPerRow, distanceBetweenRows, distanceDeskToWallLeft, distanceToWallLeft);
                    updateRoom(layout, currentSelected);
                }
            }
        );

        newRoom.setOnAction(actionEvent -> {
            currentSelected = getDefaultRoom();
            updateRoom(layout, currentSelected);
            updateFields(currentSelected, description, roomSize, desk, firstRow, tableSize, amountRows, amountTablesPerRow, distanceBetweenRows, distanceDeskToWallLeft, distanceToWallLeft);
            dropDown.getSelectionModel().clearSelection();
        });

        save.setOnAction(actionEvent -> {
            try {
                if (currentSelected.getUid() == 0) {
                    int id = 1;
                    if (!roomList.isEmpty()) {
                        id = roomList.get(roomList.size() - 1).getUid() + 1;
                    }
                    currentSelected.setUid(id);
                }
                roomRepo.save(currentSelected);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                roomList = roomRepo.getAll();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            save.setDisable(true);
            dropDown.setItems(FXCollections.observableArrayList(roomList));
        });

        currentSelected = dropDown.getValue();
        if (currentSelected == null) {
            currentSelected = Room.emptyRoom();
        }
        updateFields(currentSelected, description, roomSize, desk, firstRow, tableSize, amountRows, amountTablesPerRow, distanceBetweenRows, distanceDeskToWallLeft, distanceToWallLeft);
        var left = new VBox(dropDown, grid);
        left.setAlignment(Pos.TOP_CENTER);
        left.setSpacing(20);
        layout.getChildren().addAll(left, new RoomLayout(currentSelected));
    }

    private void updateFields(Room room,
                              LabeledTextField description,
                              LabeledDoubleNumberField roomSize,
                              LabeledNumberField desk,
                              LabeledNumberField firstRow,
                              LabeledDoubleNumberField tableSize,
                              LabeledNumberField amountRows,
                              LabeledNumberField amountTablesPerRow,
                              LabeledNumberField distanceBetweenRows,
                              LabeledNumberField distanceDeskToWallLeft,
                              LabeledNumberField distanceToWallLeft) {
        if (room != null) {

            description.setText(room.getDescription());
            roomSize.setText1(room.getLength());
            roomSize.setText2(room.getWidth());
            desk.setText(room.getDistanceDeskToBoard());
            firstRow.setText(room.getDistanceFirstRowToDesk());
            tableSize.setText1(room.getTableLength());
            tableSize.setText2(room.getTableWidth());
            amountRows.setText(room.getRows());
            amountTablesPerRow.setText(room.getTablesPerRow());
            distanceBetweenRows.setText(room.getBetweenRows());
            distanceDeskToWallLeft.setText(room.getDeskDistanceToWallLeft());
            distanceToWallLeft.setText(room.getDistanceToWallLeft());
        }
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

    private Room getDefaultRoom() {
        return new Room(1000, 700, 100, 100, 80, 100, 5, 6, 50, 300, 100, "New Room");
    }

}
