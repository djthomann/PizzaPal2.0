package pizzapal.ui.view.entities.item;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pizzapal.model.domain.entities.Item;
import pizzapal.ui.view.entities.EntityView;
import pizzapal.utils.Helper;

public class ItemView extends EntityView<Item> {

    private final Rectangle itemRectangle;

    private final Rectangle ghostRectangle;

    public ItemView(Color color, float width, float height, float posX, float posY) {

        this.setPrefSize(width, height);

        itemRectangle = new Rectangle(width, height);
        itemRectangle.setFill(color);

        ghostRectangle = new Rectangle(width, height);
        ghostRectangle.setFill(Color.DARKGRAY);

        setLayoutX(posX);
        setLayoutY(posY);

        this.getChildren().addAll(ghostRectangle, itemRectangle);
    }

    public void updateFromModel(Item item) {
        resetRectangle();

        this.setLayoutX(Helper.convertMetersToPixel(item.getPosX()));
        this.setLayoutY(
                Helper.getPixelPositionYInStorage(item.getBoard().getSupportLeft().getStorage(), item.getPosY()));

    }

    public void move(float posX, float posY) {
        itemRectangle.setLayoutX(0);
        itemRectangle.setLayoutY(0);

        ghostRectangle.setLayoutX(0);
        ghostRectangle.setLayoutY(0);

        setLayoutX(posX);
        setLayoutY(posY);
    }

    public void resetRectangle() {
        itemRectangle.setLayoutX(0);
        itemRectangle.setLayoutY(0);
    }

    public void moveRectangle(float posX, float posY) {
        itemRectangle.setLayoutX(posX);
        itemRectangle.setLayoutY(posY);
    }

}
