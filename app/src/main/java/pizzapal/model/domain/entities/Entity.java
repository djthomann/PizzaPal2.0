package pizzapal.model.domain.entities;

import pizzapal.model.observability.ObservableField;

// TODO: MOVE more behaviour to this super class
public abstract class Entity {

    protected ObservableField<Float> width;
    protected ObservableField<Float> height;

    protected ObservableField<Float> posX;
    protected ObservableField<Float> posY;

    protected ObservableField<Boolean> deleted = new ObservableField<>(false);

    protected Entity() {

    }

    protected Entity(float width, float height, float posX, float posY) {
        this.width = new ObservableField<Float>(width);
        this.height = new ObservableField<Float>(height);
        this.posX = new ObservableField<Float>(posX);
        this.posY = new ObservableField<Float>(posY);
    }

    public void delete() {
        deleted.setValue(true);
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

    public ObservableField<Boolean> deleteObservable() {
        return deleted;
    }

}
