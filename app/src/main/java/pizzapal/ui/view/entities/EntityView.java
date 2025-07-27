package pizzapal.ui.view.entities;

import javafx.scene.layout.Pane;
import pizzapal.model.domain.entities.Entity;

public abstract class EntityView<E extends Entity> extends Pane {

    public abstract void move(float posX, float posY);

    public abstract void updateFromModel(E e);

    public abstract void moveRectangle(float posX, float posY);

    public abstract void resetRectangle();

}
