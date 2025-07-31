package pizzapal.model.domain.entities;

import javafx.scene.paint.Color;

public class Ingredient {

    private String name;

    private Color color;

    public Ingredient(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
