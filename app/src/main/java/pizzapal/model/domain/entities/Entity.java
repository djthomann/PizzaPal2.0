package pizzapal.model.domain.entities;

// TODO: MOVE more behaviour to this super class
public abstract class Entity {

    protected float width;
    protected float height;

    protected float posX;
    protected float posY;

    protected Entity() {

    }

    protected Entity(float width, float height, float posX, float posY) {
        this.width = width;
        this.height = height;
        this.posX = posX;
        this.posY = posY;
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

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

}
