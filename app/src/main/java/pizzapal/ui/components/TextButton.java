package pizzapal.ui.components;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import pizzapal.ui.UIConfig;

public class TextButton extends Button {

    public TextButton(String text) {
        super(text);

        this.setMinSize(200, 50);
        this.setBackground(UIConfig.BUTTON_NORMAL_BACKGROUND);
        this.setBorder(UIConfig.BUTTON_NORMAL_BORDER);

        this.setOnMouseEntered(_ -> {
            setBackground(UIConfig.BUTTON_HOVER);
            setCursor(Cursor.HAND);
        });

        this.setOnMouseExited(_ -> {
            setBackground(UIConfig.BUTTON_NORMAL_BACKGROUND);
            setCursor(Cursor.DEFAULT);
        });
    }

}
