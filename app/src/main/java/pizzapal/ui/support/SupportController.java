package pizzapal.ui.support;

import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import pizzapal.Helper;
import pizzapal.model.Support;
import pizzapal.model.storage.StorageController;

public class SupportController {

    private final Support model;

    private final StorageController storageController;

    private final SupportView view;

    private double dragOffsetX;
    private double dragOffsetY;

    public SupportController(StorageController storageController, Support support) {

        this.storageController = storageController;
        this.model = support;

        float widthPx = Helper.convertMetersToPixel(model.getWidth());
        float heightPx = Helper.convertMetersToPixel(model.getHeight());
        float posX = Helper.convertMetersToPixel(model.getPositionX());
        float posY = Helper.convertMetersToPixel(model.getPositionY());

        view = new SupportView(widthPx, heightPx, posX, posY);

        initDragAndDrop();

    }

    public SupportView getView() {
        return view;
    }

    public void initDragAndDrop() {

        view.setOnMousePressed(e -> {

            MouseButton button = e.getButton();

            switch (button) {
                case PRIMARY -> {
                    dragOffsetX = e.getSceneX() - view.getLayoutX();
                    dragOffsetY = e.getSceneY() - view.getLayoutY();
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
                    double newX = e.getSceneX() - dragOffsetX;
                    double newY = e.getSceneY() - dragOffsetY;

                    if (storageController.moveSupport(model, (float) newX, (float) newY)) {
                        view.move((float) newX, (float) newY);
                    } else {
                        view.resetRectangle();
                    }

                    dragOffsetX = 0;
                    dragOffsetY = 0;
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
