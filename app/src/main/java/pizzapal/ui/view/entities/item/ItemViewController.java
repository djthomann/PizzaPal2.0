package pizzapal.ui.view.entities.item;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Item;
import pizzapal.ui.view.entities.EntityViewController;
import pizzapal.utils.Helper;

public class ItemViewController extends EntityViewController<Item> {

    private Item item;

    private StorageController storageController;

    public ItemViewController(StorageController storageController, Item item) {

        super(storageController, item, new ItemView(item.getColor(), Helper.convertMetersToPixel(item.getWidth()),
                Helper.convertMetersToPixel(item.getHeight()), Helper.convertMetersToPixel(item.getPosX()),
                Helper.getPixelPositionYInStorage(storageController.getStorage(), item.getPosY() + item.getHeight())));
        this.item = item;
        this.storageController = storageController;

        item.addListener((model, type) -> {

            switch (type) {
                case MOVE -> {
                    view.updateFromModel(model);
                }
                case DELETE -> {
                    Parent parent = view.getParent();
                    if (parent instanceof Pane pane) {
                        pane.getChildren().remove(view);
                    }
                }
            }

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
