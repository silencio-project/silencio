/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

module de.dhbw.silencio {
    requires javafx.controls;
    requires java.desktop;
    requires lombok;
    requires opencsv;

    exports de.dhbw.silencio.ui.scene;
    exports de.dhbw.silencio.ui;
}
