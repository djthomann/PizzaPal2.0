package pizzapal.model.domain.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import pizzapal.model.domain.entities.SerializableColor;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.UIConfig;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Storage {

    public int id;

    private String name;

    // In meters
    private float width;

    // In meters
    private float height;

    private SerializableColor color;

    private List<Support> supports;

    public Storage() {
        supports = new ArrayList<>();
    }

    public Storage(float width, float height) {
        this(new SerializableColor(UIConfig.STORAGE_BACKGROUND_COLOR), width, height);
    }

    public Storage(SerializableColor color, float width, float height) {
        this("Empty name", color, width, height);
    }

    public Storage(String name, SerializableColor color, float width, float height) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.color = color;

        supports = new ArrayList<>();
    }

    public void initListeners() {
        for (Support s : supports) {
            s.initListeners();
        }
    }

    @JsonIgnore
    public boolean isEmpty() {
        return supports.isEmpty();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public SerializableColor getColor() {
        return color;
    }

    public void setColor(SerializableColor color) {
        this.color = color;
    }

    public List<Support> getSupports() {
        return supports;
    }

    public void setSupports(List<Support> supports) {
        this.supports = supports;
    }

    public void addSupport(Support support) {
        supports.add(support);
    }

    public void removeSupport(Support support) {
        supports.remove(support);
    }

}
