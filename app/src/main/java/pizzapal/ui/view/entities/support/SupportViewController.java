package pizzapal.ui.view.entities.support;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.view.entities.EntityViewController;
import pizzapal.utils.Helper;
import pizzapal.utils.ToolState;

public class SupportViewController extends EntityViewController<Support> {

    private final Support support;

    private final StorageController storageController;

    public SupportViewController(StorageController storageController, ToolState toolState, Support support) {
        super(storageController, toolState, support,
                new SupportView(support.getColor().getColor(), Helper.convertMetersToPixel(support.getWidth()),
                        Helper.convertMetersToPixel(support.getHeight()),
                        Helper.convertMetersToPixel(support.getPosX()),
                        Helper.getPixelPositionYInStorage(support.getStorage(), support)));
        this.storageController = storageController;
        this.support = support;

        support.addListener((model, type) -> {

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
        if (!storageController.moveSupport(support, Helper.convertPixelToMeters(xInView),
                Helper.convertPixelToMeters(yInView))) {
            view.resetRectangle();
        }
    }
}
