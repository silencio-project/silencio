package ui.util;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class Typography {

    // https://motleybytes.com/w/JavaFxFonts

    public static Font titleFont() {
        return Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 40);
    }

    public static Font standardFont() {
        return Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15);
    }

    public static Font textFieldTitleFont() {
        return Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 12);
    }

    public static Font timestampFont() {
        return Font.font("Verdana", FontWeight.NORMAL, FontPosture.REGULAR, 14);
    }

    public static Font sceneHeadingFont() {
        return Font.font("Arial", FontWeight.NORMAL, FontPosture.REGULAR, 30);
    }
}
