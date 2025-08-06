package pizzapal.model.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javafx.scene.paint.Color;

public class SerializableColor {

    @JsonIgnore
    private Color color;

    public SerializableColor() {
        this.color = Color.BLACK;
    }

    public SerializableColor(Color color) {
        this.color = color;
    }

    @JsonProperty("color")
    public String getColorString() {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }

    @JsonProperty("color")
    public void setColorString(String colorString) {
        this.color = Color.web(colorString);
    }

    public Color getColor() {
        return color;
    }
}
