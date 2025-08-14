package pizzapal.ui.view.entities.item;

import javafx.scene.paint.Color;
import pizzapal.model.domain.entities.Item;
import pizzapal.ui.view.entities.EntityView;

public class ItemView extends EntityView<Item> {

    public ItemView(Color color, float width, float height, float posX, float posY) {
        super(color, width, height, posX, posY);
    }

}
