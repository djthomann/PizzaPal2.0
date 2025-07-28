package pizzapal.ui.components;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import pizzapal.ui.UIConfig;

public class TextButton extends Button {

    // TODO: Animate Color

    public TextButton(String text) {
        super(text);

        this.setMinSize(200, 50);
        this.setBackground(UIConfig.BUTTON_NORMAL_BACKGROUND);
        this.setBorder(UIConfig.BUTTON_NORMAL_BORDER);
        this.setFont(UIConfig.SMALL_BOLD_FONT);

        this.setOnMouseEntered(_ -> {
            setBackground(UIConfig.BUTTON_GRADIENT_BACKGROUND);
            setCursor(Cursor.HAND);
        });

        this.setOnMouseExited(_ -> {
            setBackground(UIConfig.BUTTON_NORMAL_BACKGROUND);
            setCursor(Cursor.DEFAULT);
        });
    }

}
