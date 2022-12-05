package ui.components;

import ui.util.Typography;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class LabeledTextField extends GridPane {

    private final TextField textField;

    public LabeledTextField(String title) {
        textField = new TextField();
        textField.setPrefWidth(200);
        var label = new Label(title);
        label.setFont(Typography.textFieldTitleFont());
        this.setPadding(new Insets(0,20,10,10));
        this.setVgap(3);
        this.add(label, 0, 0);
        this.add(textField, 0, 1);
    }


    public TextField getTextField() {
        return textField;
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text){
        textField.setText(text);
    }

}
