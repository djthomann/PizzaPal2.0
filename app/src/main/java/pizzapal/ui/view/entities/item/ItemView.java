package pizzapal.ui.view.entities.item;

import javafx.scene.paint.Color;
import pizzapal.model.domain.entities.Item;
import pizzapal.ui.view.entities.EntityView;
import pizzapal.utils.Helper;

public class ItemView extends EntityView<Item> {

    public ItemView(Color color, float width, float height, float posX, float posY) {
        super(color, width, height, posX, posY);
    }

    public void updateFromModel(Item item) {

        System.out.println("UPDATE FROM MODEL");

        resetRectangle();

        this.setLayoutX(Helper.convertMetersToPixel(item.getPosX()));
        this.setLayoutY(
                Helper.getPixelPositionYInStorage(item.getBoard().getSupportLeft().getStorage(), item.getPosY()));

    }

    @Override
    public void updateFromModelEdit(Item item) {

        float newWidth = Helper.convertMetersToPixel(item.getWidth());
        float newHeight = Helper.convertMetersToPixel(item.getHeight());
        Color newColor = item.getColor().getColor();

        this.setPrefSize(newWidth, newHeight);

        setLayoutY(Helper.getPixelPositionYInStorage(item.getBoard().getSupportLeft().getStorage(), item.getPosY()));

        entityRectangle.setWidth(newWidth);
        entityRectangle.setHeight(newHeight);
        entityRectangle.setFill(newColor);

        ghostRectangle.setWidth(newWidth);
        ghostRectangle.setHeight(newHeight);

    }

}
