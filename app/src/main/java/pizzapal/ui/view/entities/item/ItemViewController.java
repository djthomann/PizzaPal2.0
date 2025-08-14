package pizzapal.ui.view.entities.item;

import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Item;
import pizzapal.ui.view.entities.EntityViewController;
import pizzapal.utils.Helper;
import pizzapal.utils.ToolState;

public class ItemViewController extends EntityViewController<Item> {

    public ItemViewController(StorageController storageController, ToolState toolState, Item item) {

        super(storageController, toolState, item, new ItemView(item.getColor().getColor(),
                Helper.convertMetersToPixel(item.getWidth()),
                Helper.convertMetersToPixel(item.getHeight()), Helper.convertMetersToPixel(item.getPosX()),
                Helper.getPixelPositionYInStorage(storageController.getStorage(), item.getPosY())));
    }

}
