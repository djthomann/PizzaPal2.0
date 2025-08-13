package pizzapal.ui.view.app.mainmenu.submenu;

import java.util.function.UnaryOperator;

import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.converter.IntegerStringConverter;
import pizzapal.ui.components.InputFieldWithLabel;
import pizzapal.utils.Config;
import pizzapal.utils.SoundPlayer;

public class SettingsView extends SubMenuView {

    public SettingsView() {

        super("Settings", "Configure PizzaPal!");

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

        positiveIntFormatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            Config.PIXEL_PER_METER = newValue;
        });

        InputFieldWithLabel<Integer> pixelPerMeterInput = new InputFieldWithLabel<>("Pixels per meter",
                initialString, 150,
                positiveIntFormatter);

        addControlOnTop(pixelPerMeterInput);
        addControlOnTop(volumeBox);

    }

}
