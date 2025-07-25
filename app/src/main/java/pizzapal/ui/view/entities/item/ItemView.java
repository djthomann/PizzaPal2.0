package pizzapal.ui.view.entities.item;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pizzapal.Helper;
import pizzapal.model.domain.entities.Item;

public class ItemView extends Pane {

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

    public void resetRectangle() {
        itemRectangle.setLayoutX(0);
        itemRectangle.setLayoutY(0);
    }

}
