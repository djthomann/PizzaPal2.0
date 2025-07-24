package pizzapal.ui.support;

import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import pizzapal.Helper;
import pizzapal.model.entities.Support;
import pizzapal.model.storage.StorageController;
import pizzapal.ui.ViewController;

public class SupportViewController implements ViewController {

    private final Support support;

    private final StorageController storageController;

    private final SupportView view;

    public SupportViewController(StorageController storageController, Support support) {

        this.storageController = storageController;
        this.support = support;

        float widthPx = Helper.convertMetersToPixel(support.getWidth());
        float heightPx = Helper.convertMetersToPixel(support.getHeight());
        float posX = Helper.convertMetersToPixel(support.getPositionX());
        float posY = Helper.getPixelPositionYInStorage(support.getStorage(), support);

        view = new SupportView(widthPx, heightPx, posX, posY);

        initDragAndDrop();

        support.addListener(model -> {
            view.updateFromModel(model);
        });

    }

    public SupportView getView() {
        return view;
    }

    public void initDragAndDrop() {

        view.setOnMousePressed(e -> {

            MouseButton button = e.getButton();

            switch (button) {
                case PRIMARY -> {
                    view.setCursor(Cursor.MOVE);
                }
                case SECONDARY -> {
                    view.toggleContextMenu();
                }
                default -> {
                }
            }

        });

        view.setOnMouseEntered(_ -> {
            // view.setCursor(Cursor.HAND);
        });

        view.setOnMouseDragged(e -> {

            MouseButton button = e.getButton();

            switch (button) {
                case PRIMARY -> {
                    view.setCursor(Cursor.MOVE);
                    view.moveRectangle((float) e.getX(), (float) e.getY());
                }
                default -> {
                }
            }

        });

        view.setOnMouseReleased(e -> {

            MouseButton button = e.getButton();

            switch (button) {
                case PRIMARY -> {

                    if (!storageController.moveSupport(support, (float) e.getSceneX(), (float) e.getSceneY())) {
                        view.resetRectangle();
                    }
                }
                default -> {
                }
            }

            view.setCursor(Cursor.HAND);

        });

        view.setOnMouseExited(_ -> {
            // view.setCursor(Cursor.DEFAULT);
        });
    }

}
