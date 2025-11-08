package pizzapal.model.domain.entities;

import pizzapal.model.observability.ObservableField;

// TODO: MOVE more behaviour to this super class
public abstract class Entity {

    @Editable
    protected ObservableField<Float> height;

    protected ObservableField<Float> posX;
    protected ObservableField<Float> posY;

    @Editable
    protected ObservableField<SerializableColor> color;

    protected ObservableField<Boolean> deleted = new ObservableField<>(false);

    protected Entity() {
        this(new SerializableColor(), 0f, 0f, 0f);
    }

    protected Entity(SerializableColor color, float height, float posX, float posY) {
        this.height = new ObservableField<>(height);
        this.posX = new ObservableField<>(posX);
        this.posY = new ObservableField<>(posY);
        this.color = new ObservableField<>(color);
    }

    public void delete() {
        deleted.setValue(true);
    }

    public ObservableField<Float> heightObservable() {
        return height;
    }

    public float getHeight() {
        return height.getValue();
    }

    public void setHeight(float height) {
        this.height.setValue(height);
    }

    public ObservableField<Float> posXObservable() {
        return posX;
    }

    public float getPosX() {
        return posX.getValue();
    }

    public void setPosX(float posX) {
        this.posX.setValue(posX);
    }

    public ObservableField<Float> posYObservable() {
        return posY;
    }

    public float getPosY() {
        return posY.getValue();
    }

    public void setPosY(float posY) {
        this.posY.setValue(posY);
    }

    public ObservableField<SerializableColor> colorObservable() {
        return color;
    }

    public SerializableColor getColor() {
        return color.getValue();
    }

    public void setColor(SerializableColor color) {
        this.color.setValue(color);
    }

    public ObservableField<Boolean> deleteObservable() {
        return deleted;
    }

    public abstract ObservableField<Float> widthObservable();

    public abstract float getWidth();

    public abstract void setWidth(float width);

}
