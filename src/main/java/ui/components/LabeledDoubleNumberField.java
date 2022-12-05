package ui.components;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import ui.util.Typography;

/**
 * @author Moritz Thoma
 * @since 1.0.0
 */

public class LabeledDoubleNumberField extends GridPane {
    private final TextField textField1;
    private final TextField textField2;
    private int value1;
    private int value2;

    public LabeledDoubleNumberField(String title) {
        textField1 = new TextField();
        textField2 = new TextField();
        textField1.setPrefWidth(85);
        textField2.setPrefWidth(85);

        textField1.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (newValue.matches("\\d*")) {
                    value1 = Integer.parseInt(newValue);
                } else {
                    textField1.setText(oldValue);
                }
            }else {
                value1 = 0;
            }
        });
        textField2.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                if (newValue.matches("\\d*")) {
                    value2 = Integer.parseInt(newValue);
                } else {
                    textField2.setText(oldValue);
                }
            }else {
                value2 = 0;
            }
        });
        var x = new Label("X");
        x.setFont(new Font("Arial", 20));
        x.setPadding(new Insets(0, 6, 0, 6));

        var label = new Label(title);
        label.setFont(Typography.textFieldTitleFont());
        this.setPadding(new Insets(0, 20, 10, 10));
        this.setVgap(3);
        this.add(label, 0, 0,3,1);
        this.add(textField1, 0, 1);
        this.add(x, 1, 1);
        this.add(textField2, 2, 1);
    }

    public TextField getTextField1() {
        return textField1;
    }

    public int getValue1() {
        return value1;
    }

    public TextField getTextField2() {
        return textField2;
    }

    public int getValue2() {
        return value2;
    }

    public void setText1(int value) {
        textField1.setText(String.valueOf(value));
    }

    public void setText2(int value) {
        textField2.setText(String.valueOf(value));
    }
}
