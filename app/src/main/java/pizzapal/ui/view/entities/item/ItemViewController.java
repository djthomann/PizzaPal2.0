package pizzapal.ui.view.entities.item;

import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Item;
import pizzapal.ui.view.entities.EntityViewController;
import pizzapal.utils.Helper;

public class ItemViewController extends EntityViewController<Item> {

    private Item item;

    private StorageController storageController;

    public ItemViewController(StorageController storageController, Item item) {

        super(new ItemView(item.getColor(), Helper.convertMetersToPixel(item.getWidth()),
                Helper.convertMetersToPixel(item.getHeight()), Helper.convertMetersToPixel(item.getPosX()),
                Helper.getPixelPositionYInStorage(storageController.getStorage(), item.getPosY() + item.getHeight())));
        this.item = item;
        this.storageController = storageController;

        item.addListener(model -> {
            view.updateFromModel(model);
        });

    }

    @Override
    protected void onMouseReleased(float xInView, float yInView) {
        if (!storageController.moveItem(item, Helper.convertPixelToMeters(xInView),
                Helper.convertPixelPositionToHeightInStorage(storageController.getStorage(),
                        yInView))) {
            view.resetRectangle();
        }
    }

}
