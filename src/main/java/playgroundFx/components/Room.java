package playgroundFx.components;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Room extends StackPane {

    private final int roomLengthPixel;
    private final int roomWidthPixel;
    private final int distanceToBoardPixel;
    private final int tableLengthPixel;
    private final int tableWidthPixel;
    private final int betweenRowsPixel;
    private final int distanceToWallRightPixel;
    private final int distanceToWallLeftPixel;

    private final double ratio;

    public Room(int length, int width, int distanceToBoard, int tableLength, int tableWidth, int rows, int tablesPerRow, int betweenRows, int distanceToWallRight, int distanceToWallLeft) {
        this.ratio = (double) width / length;
        this.roomLengthPixel = 850;
        this.roomWidthPixel = (int) (850 * ratio);
        this.distanceToBoardPixel = (int) (distanceToBoard * ratio);
        this.tableLengthPixel = (int) (tableLength * ratio);
        this.tableWidthPixel = (int) (tableWidth * ratio);
        this.betweenRowsPixel = (int) (betweenRows * ratio);
        this.distanceToWallRightPixel = (int) (distanceToWallRight * ratio);
        this.distanceToWallLeftPixel = (int) (distanceToWallLeft * ratio);
        this.setPrefWidth(1150);
        this.setPrefHeight(850);
        this.setBackground(Background.fill(Color.LIGHTGRAY));
        this.setBorder(Border.stroke(Color.BLACK));

        var room = new Pane();
        room.setPrefHeight(850);
        room.setPrefWidth(roomWidthPixel);
        room.setMaxWidth(roomWidthPixel);
        room.setBorder(Border.stroke(Color.BLACK));
        room.setBackground(Background.fill(Color.WHITE));

        int distanceY = distanceToBoard;
        for (int i = 0; i < rows; i++) {
            var tr = new TableRow(tablesPerRow, tableLengthPixel, tableWidthPixel);
            tr.setLayoutX(distanceToWallLeftPixel);
            tr.setLayoutY(850 - distanceY);
            room.getChildren().add(tr);
            distanceY = distanceY + tableLengthPixel + betweenRowsPixel;
        }

        var desk = new Table(tableLengthPixel, tableWidthPixel*2);
        desk.setLayoutX(roomWidthPixel / 2.5 - tableLength / 2.0);
        desk.setLayoutY(850-(distanceToBoardPixel / 4.0 + tableWidthPixel));
        room.getChildren().add(desk);
        this.getChildren().add(room);

    }

    public int getRoomLengthPixel() {
        return roomLengthPixel;
    }

    public int getRoomWidthPixel() {
        return roomWidthPixel;
    }

    public int getDistanceToBoardPixel() {
        return distanceToBoardPixel;
    }

    public int getTableLengthPixel() {
        return tableLengthPixel;
    }

    public int getTableWidthPixel() {
        return tableWidthPixel;
    }

    public int getBetweenRowsPixel() {
        return betweenRowsPixel;
    }

    public int getDistanceToWallRightPixel() {
        return distanceToWallRightPixel;
    }

    public int getDistanceToWallLeftPixel() {
        return distanceToWallLeftPixel;
    }

    public double getRatio() {
        return ratio;
    }
}
