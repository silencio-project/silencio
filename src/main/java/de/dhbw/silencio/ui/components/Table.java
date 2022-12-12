package de.dhbw.silencio.ui.components;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class Table extends Region {

    private int length;
    private int width;
    private Line line;

    public Table(int length, int width, boolean withPointer) {
        this.length = length;
        this.width = width;
        this.setPrefHeight(length);
        this.setPrefWidth(width);
        this.setBackground(Background.fill(Color.DIMGRAY));
        this.setBorder(Border.stroke(Color.BLACK));
        if (withPointer) {
            line = new Line();
            line.setStartX(width / 2.0);
            line.setStroke(Color.RED);
            line.setStrokeWidth(5);
            line.setSmooth(true);
            line.setStartY(0);
            line.setVisible(false);
            this.getChildren().add(line);
        }
    }

    public void updateAngle(double angle, double length) {
        if (angle != 0 && angle < 180 && angle != 90) {
            angle = 180 - angle;
            var ratio = Math.tan(Math.toRadians(angle));
            double a = 0;
            double x = ratio > 0 ? a : -1 * a;
            double y = ratio > 0 ? (-1 * (a * ratio)) : (a * ratio);
            while (distance(width / 2.0, 0, x, y) < length) {
                a++;
                x = ratio > 0 ? a : -1 * a;
                y = ratio > 0 ? (-1 * (a * ratio)) : (a * ratio);
            }
            line.setEndX(x > 0 ? x + (width / 2.0) : x - (width / 2.0));
            line.setEndY(y);
            line.setVisible(true);
        } else if (angle == 90) {
            line.setEndX(width / 2.0);
            line.setEndY(-length);
            line.setVisible(true);
        } else {
            line.setVisible(false);
        }
    }

    private double distance(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }


    public int getTableLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


    public int getTableWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
