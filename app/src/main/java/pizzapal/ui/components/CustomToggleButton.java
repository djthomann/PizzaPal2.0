package pizzapal.ui.components;

import javafx.scene.control.ToggleButton;

public class CustomToggleButton extends ToggleButton {

    public CustomToggleButton(String text) {
        super(text);

        this.getStylesheets().add(getClass().getResource("/styles/button.css").toExternalForm());

    }

}
