package pizzapal.ui.view.app.mainmenu.submenu.settings;

import java.util.function.UnaryOperator;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.converter.IntegerStringConverter;
import pizzapal.ui.components.InputFieldWithLabel;
import pizzapal.ui.components.TextButton;
import pizzapal.ui.view.app.mainmenu.submenu.SubMenuView;
import pizzapal.utils.Config;
import pizzapal.utils.SoundPlayer;

public class SettingsView extends SubMenuView {

    private final TextButton controlsButton;

    private final ControlsView controlsView;

    public SettingsView() {

        super("Settings", "Configure PizzaPal!", SubMenuPosition.BOTTOM_LEFT);

        controlsView = new ControlsView();

        Label label = new Label("Volume");
        label.setFont(Font.font(10.5));

        Slider volumeSlider = new Slider(0, 1, SoundPlayer.getVolume());
        volumeSlider.setMaxWidth(150);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);

        volumeSlider.setOnMouseEntered(e -> {
            setCursor(Cursor.HAND);
        });

        volumeSlider.setOnMouseExited(e -> {
            setCursor(Cursor.DEFAULT);
        });

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            float volume = newValue.floatValue();
            SoundPlayer.setVolume(volume);
        });

        VBox volumeBox = new VBox(label, volumeSlider);
        volumeBox.setSpacing(3);
        volumeBox.setMaxWidth(150);

        UnaryOperator<TextFormatter.Change> positiveIntFilter = change -> {
            String newText = change.getControlNewText();
            try {
                int value = Integer.parseInt(newText);
                if (value > 0) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // invalid
            }
            return null;
        };

        int initialInt = Config.PIXEL_PER_METER;
        String initialString = String.valueOf(initialInt);

        TextFormatter<Integer> positiveIntFormatter = new TextFormatter<>(
                new IntegerStringConverter(),
                Config.PIXEL_PER_METER,
                positiveIntFilter);

        InputFieldWithLabel<Integer> pixelPerMeterInput = new InputFieldWithLabel<>("Pixels per meter",
                initialString, 150,
                positiveIntFormatter);

        pixelPerMeterInput.getTextField().textProperty().addListener((obs, oldValue, newValue) -> {
            Config.PIXEL_PER_METER = Integer.parseInt(newValue);
        });

        controlsButton = new TextButton("Controls");

        addControlOnTop(controlsButton);
        addControlOnTop(pixelPerMeterInput);
        addControlOnTop(volumeBox);

    }

    public TextButton getControlsButton() {
        return controlsButton;
    }

    public ControlsView getControlsView() {
        return controlsView;
    }

}
