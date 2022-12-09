package de.dhbw.silencio.ui.components;

import de.dhbw.silencio.storage.Room;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class RoomLayout extends StackPane {

    private final Table desk;
    private Room room;

    public RoomLayout(Room room) {
        this.room = room;
        var roomLengthPixel = 850;
        var ratio = (double) roomLengthPixel / room.getLength();
        var roomWidthPixel = (int) (roomLengthPixel * ratio);

        var distanceDeskToBoardPixel = (int) (room.getDistanceDeskToBoard() * ratio);
        var distanceFirstRowToDesk = (int) (room.getDistanceFirstRowToDesk() * ratio);
        var distanceDeskToWallLeftPixel = (int) (room.getDeskDistanceToWallLeft() * ratio);
        var tableLengthPixel = (int) (room.getTableLength() * ratio);
        var tableWidthPixel = (int) (room.getTableWidth() * ratio);
        var betweenRowsPixel = (int) (room.getBetweenRows() * ratio);
        var distanceToWallLeftPixel = (int) (room.getDistanceToWallLeft() * ratio);

        this.setPrefHeight(850);
        this.setPrefWidth(1150);
        this.setBackground(Background.fill(Color.LIGHTGRAY));
        this.setBorder(Border.stroke(Color.BLACK));

        var roomLayout = new Pane();
        roomLayout.setPrefHeight(roomLengthPixel);
        roomLayout.setPrefWidth(roomWidthPixel);
        roomLayout.setMaxWidth(roomWidthPixel);
        roomLayout.setBorder(Border.stroke(Color.BLACK));
        roomLayout.setBackground(Background.fill(Color.WHITE));

        int distanceY = distanceFirstRowToDesk + 2 * tableLengthPixel + distanceDeskToBoardPixel;
        for (int i = 0; i < room.getRows(); i++) {
            var tableRow = new TableRow(room.getTablesPerRow(), tableLengthPixel, tableWidthPixel);
            tableRow.setLayoutX(distanceToWallLeftPixel);
            tableRow.setLayoutY(roomLengthPixel - distanceY);
            roomLayout.getChildren().add(tableRow);
            distanceY = distanceY + tableLengthPixel + betweenRowsPixel;
        }

        desk = new Table(tableLengthPixel, tableWidthPixel * 2, true);
        desk.setLayoutX(distanceDeskToWallLeftPixel);
        desk.setLayoutY(850 - (distanceDeskToBoardPixel + tableLengthPixel));

        roomLayout.getChildren().add(desk);
        this.getChildren().add(roomLayout);

    }

    public void updatePointer(int angle, int length) {
        desk.updateAngle(angle, length);
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
