package playgroundFx.components;

import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

public class Table extends Region {

    public Table(int height, int width) {
        this.setPrefHeight(height);
        this.setPrefWidth(width);
        this.setBackground(Background.fill(Color.DIMGRAY));
        this.setBorder(Border.stroke(Color.BLACK));
    }
}
