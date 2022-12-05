package ui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ui.util.Typography;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class LabeledNumberField extends GridPane {

    private final TextField textField;
    private int value;

    public LabeledNumberField(String title) {
        textField = new TextField();
        textField.setPrefWidth(200);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (newValue.matches("\\d*")) {
                    value = Integer.parseInt(newValue);
                } else {
                    textField.setText(oldValue);
                }
            }else {
                value = 0;
            }
        });


        var label = new Label(title);
        label.setFont(Typography.textFieldTitleFont());
        this.setPadding(new Insets(0, 20, 10, 10));
        this.setVgap(3);
        this.add(label, 0, 0);
        this.add(textField, 0, 1);
    }

    public TextField getTextField() {
        return textField;
    }

    public int getValue() {
        return value;
    }

    public void setText(int value) {
        textField.setText(String.valueOf(value));
    }
}


