package pizzapal.ui.view.entities;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import pizzapal.model.domain.entities.Entity;

public abstract class EntityViewController<E extends Entity> {

    protected EntityView<E> view;

    protected EntityViewController(EntityView<E> view) {
        this.view = view;
        initDragAndDrop();
    }

    public void initDragAndDrop() {

        view.setOnMousePressed(e -> {

            MouseButton button = e.getButton();

            switch (button) {
                case PRIMARY -> {
                    view.setCursor(Cursor.MOVE);
                }
                case SECONDARY -> {
                    // view.toggleContextMenu();
                }
                default -> {
                }
            }

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

                    Point2D localPoint = view.getParent().sceneToLocal(e.getSceneX(), e.getSceneY());
                    float xInView = (float) localPoint.getX();
                    float yInView = (float) localPoint.getY();

                    onMouseReleased(xInView, yInView);

                    /*
                     * if (!storageController.moveBoard(board, Helper.convertPixelToMeters(xInView),
                     * Helper.convertPixelPositionToHeightInStorage(storageController.getStorage(),
                     * yInView))) {
                     * view.resetRectangle();
                     * }
                     */
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

    protected abstract void onMouseReleased(float xInView, float yInView);

    public Pane getView() {
        return view;
    }

}
