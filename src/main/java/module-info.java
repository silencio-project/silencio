/**
 * @author Paul Antoni
 * @author Luis Binzenhöfer
 * @author Daniel Eremeev
 * @author Yannick Kirschen
 * @author Moritz Thoma
 */
module de.dhbw.silencio {
    requires javafx.controls;
    requires java.desktop;
    requires static lombok;
    requires com.opencsv;
    requires java.sql;

    exports de.dhbw.silencio.ui.scene;
    exports de.dhbw.silencio.ui;
    exports de.dhbw.silencio.storage;
}
