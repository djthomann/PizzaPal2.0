package pizzapal.model.domain.entities;

import pizzapal.model.observability.ObservableField;

public class VariableWidthEntity extends Entity {

    protected ObservableField<Float> width;

    protected VariableWidthEntity() {
        super();
        width = new ObservableField<Float>(null);
    }

    protected VariableWidthEntity(SerializableColor color, float width, float height, float posX, float posY) {
        super(color, height, posX, posY);
        this.width = new ObservableField<Float>(width);
    }

    public ObservableField<Float> widthObservable() {
        return width;
    }

    public float getWidth() {
        return width.getValue();
    }

    public void setWidth(float width) {
        this.width.setValue(width);
    }

}
