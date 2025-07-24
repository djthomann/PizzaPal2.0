package pizzapal.ui;

public interface EntityView {

    public void move(float posX, float posY);

    public void updateFromModel();

    public void moveRectangle(float posX, float posY);

    public void resetRectangle();

}
