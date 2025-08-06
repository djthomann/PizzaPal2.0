package pizzapal.model.domain.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javafx.scene.paint.Color;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Ingredient {

    public String id;

    private String name;

    private SerializableColor color;

    public Ingredient() {

    }

    public Ingredient(String name, Color color) {
        this.name = name;
        this.color = new SerializableColor(color);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SerializableColor getColor() {
        return color;
    }

    public void setColor(SerializableColor color) {
        this.color = color;
    }

}
