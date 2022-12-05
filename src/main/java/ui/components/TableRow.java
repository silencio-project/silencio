package ui.components;

import javafx.scene.layout.HBox;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class TableRow extends HBox {

    public TableRow(int tables , int tableLength, int tableWidth) {
        for (int i = 0; i < tables; i++) {
            this.getChildren().add(new Table(tableLength, tableWidth, false));
        }
    }
}
