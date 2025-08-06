package pizzapal.model.domain.core;

import java.util.ArrayList;
import java.util.List;

import pizzapal.model.domain.entities.Support;

public class Storage {

    // In meters
    private float width;

    // In meters
    private float height;

    private List<Support> supports;

    public Storage(float width, float height) {
        this.width = width;
        this.height = height;

        supports = new ArrayList<>();
    }

    public boolean isEmpty() {
        return supports.isEmpty();
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
