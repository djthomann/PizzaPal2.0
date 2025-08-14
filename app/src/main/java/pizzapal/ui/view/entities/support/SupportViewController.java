package pizzapal.ui.view.entities.support;

import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.view.entities.EntityViewController;
import pizzapal.utils.Helper;
import pizzapal.utils.ToolState;

public class SupportViewController extends EntityViewController<Support> {

    public SupportViewController(StorageController storageController, ToolState toolState, Support support) {
        super(storageController, toolState, support,
                new SupportView(support.getColor().getColor(), Helper.convertMetersToPixel(support.getWidth()),
                        Helper.convertMetersToPixel(support.getHeight()),
                        Helper.convertMetersToPixel(support.getPosX()),
                        Helper.getPixelPositionYInStorage(support.getStorage(), support)));
    }

}
