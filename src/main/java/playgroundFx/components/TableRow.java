package playgroundFx.components;

import javafx.scene.layout.HBox;

public class TableRow extends HBox {

    public TableRow(int tables , int tableLength, int tableWidth) {
        for (int i = 0; i < tables; i++) {
            this.getChildren().add(new Table(tableLength, tableWidth));
        }
    }
}
