package playgroundFx.room_configurator;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import playgroundFx.components.*;

public class RoomConfigurator extends DefaultScene {


    public RoomConfigurator(Stage stage) {
        super(new HBox(), stage.getWidth(), stage.getHeight(), stage, "RoomConfigurator");
        var layout = (HBox) this.getParentContent();

        var grid = new GridPane();
        var col1 = new ColumnConstraints();
        var col2 = new ColumnConstraints();
        col1.setPercentWidth(30);
        col2.setPercentWidth(70);

        var cm = new Label("* in cm ");
        cm.setPadding(new Insets(0, 0, 10, 10));

        var description = new LabeledTextField("Description");
        var roomSize = new LabeledDoubleNumberField("Room Size *");
        var firstRow = new LabeledNumberField("First Row: Distance to the Board *");
        var tableSize = new LabeledDoubleNumberField("Table Size *");
        var amountRows = new LabeledNumberField("Amount Rows *");
        var amountTablesPerRow = new LabeledNumberField("Amount tables per row *");
        var distanceBetweenRows = new LabeledNumberField("Distance between rows *");
        var distanceToWallRight = new LabeledNumberField("Distance to wall right *");
        var distanceToWallLeft = new LabeledNumberField("Distance to wall left *");

        var refresh = new Button("Refresh");
        refresh.setOnAction(actionEvent -> {
            for (var child : layout.getChildren()) {
                if (child instanceof Room) {
                    layout.getChildren().remove(child);
                    layout.getChildren().add(new Room(
                            roomSize.getValue1(),
                            roomSize.getValue2(),
                            firstRow.getValue(),
                            tableSize.getValue1(),
                            tableSize.getValue2(),
                            amountRows.getValue(),
                            amountTablesPerRow.getValue(),
                            distanceBetweenRows.getValue(),
                            distanceToWallRight.getValue(),
                            distanceToWallLeft.getValue()
                    ));
                    break;
                }
            }
        });

        var save = new Button("Save");
        var hButtons = new HBox(refresh, save);
        hButtons.setAlignment(Pos.BASELINE_CENTER);

        grid.add(description, 0, 0);
        grid.add(roomSize, 0, 1);
        grid.add(firstRow, 0, 2);
        grid.add(tableSize, 0, 3);
        grid.add(amountRows, 0, 4);
        grid.add(amountTablesPerRow, 0, 5);
        grid.add(distanceBetweenRows, 0, 6);
        grid.add(distanceToWallRight, 0, 7);
        grid.add(distanceToWallLeft, 0, 8);
        grid.add(cm, 0, 9);
        grid.add(hButtons, 0, 10);

        layout.getChildren().addAll(grid, new Room(1000, 700, 250, 80, 100, 7, 6, 50, 100, 100));

    }

    private void refreshRoom() {
        System.out.println(this.getParentContent());
    }

}
