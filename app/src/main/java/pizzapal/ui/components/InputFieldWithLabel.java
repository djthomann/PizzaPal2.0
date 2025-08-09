package pizzapal.ui.components;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class InputFieldWithLabel extends VBox {

    TextField textField;

    public InputFieldWithLabel(String labelText, String initialText, int width) {
        Label label = new Label(labelText);
        label.setFont(Font.font(10.5));
        textField = new TextField(initialText);
        textField.setMaxWidth(width);

        this.getChildren().addAll(label, textField);
        this.setMaxWidth(width);

    }

    public String getText() {
        return textField.getText();
    }

}
