package pizzapal.ui.components;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

// TODO: Make better pls
public class InputFieldWithLabel<T> extends VBox {

    TextField textField;

    public InputFieldWithLabel(String labelText, String initialText, int width) {
        this(labelText, initialText, width, null);
    }

    public InputFieldWithLabel(String labelText, String initialText, int width, TextFormatter<T> formatter) {
        Label label = new Label(labelText);
        label.setFont(Font.font(10.5));
        textField = new TextField(initialText);
        textField.setMaxWidth(width);

        if (formatter != null) {
            textField.setTextFormatter(formatter);
        }

        this.getChildren().addAll(label, textField);
        this.setSpacing(3);
        this.setMaxWidth(width);
    }

    public String getText() {
        return textField.getText();
    }

    public TextField getTextField() {
        return textField;
    }

}
