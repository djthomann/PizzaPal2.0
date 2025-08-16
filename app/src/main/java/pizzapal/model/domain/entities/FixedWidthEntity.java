package pizzapal.model.domain.entities;

import pizzapal.model.observability.ObservableField;

public class FixedWidthEntity extends Entity {

    @Editable
    protected ObservableField<Float> width;

    protected FixedWidthEntity() {
        super();
        width = new ObservableField<>(null);
    }

    protected FixedWidthEntity(SerializableColor color, float width, float height, float posX, float posY) {
        super(color, height, posX, posY);
        this.width = new ObservableField<>(width);
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
