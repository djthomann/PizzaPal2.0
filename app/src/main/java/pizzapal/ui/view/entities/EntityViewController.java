package pizzapal.ui.view.entities;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Entity;
import pizzapal.ui.UIConfig;
import pizzapal.utils.Helper;

public abstract class EntityViewController<E extends Entity> {

    private StorageController storageController;

    protected EntityView<E> view;
    protected E entity;

    private Double offsetX;
    private Double offsetY;

    private final ContextMenu contextMenu;

    protected EntityViewController(StorageController storageController, E entity, EntityView<E> view) {
        this.storageController = storageController;
        this.entity = entity;
        this.view = view;

        contextMenu = new ContextMenu();
        initContextMenu();

        initDragAndDrop();
    }

    public void initContextMenu() {

        // contextMenu.setStyle("-fx-background-color: black; -fx-border-color: black;
        // -fx-border-width: 1;");

        MenuItem item1 = new MenuItem("Option 1");
        item1.setOnAction(e -> System.out.println("Option 1 geklickt"));

        ImageView icon1 = new ImageView(Helper.loadImage("/icons/edit.png"));
        icon1.setFitWidth(16);
        icon1.setFitHeight(16);
        item1.setGraphic(icon1);
        item1.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");

        MenuItem item2 = new MenuItem("Delete");
        item2.setOnAction(e -> {
            storageController.delete(entity);
        });

        ImageView icon2 = new ImageView(Helper.loadImage("/icons/trash.png"));
        icon2.setFitWidth(16);
        icon2.setFitHeight(16);
        item2.setGraphic(icon2);
        item2.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");

        contextMenu.getItems().addAll(item1, item2);
    }

    public void showContextMenu() {

        Bounds bounds = view.localToScreen(view.getBoundsInLocal());

        double x = bounds.getMinX() + view.widthProperty().get() + UIConfig.CONTEXT_MENU_OFFSET_PIXEL;
        double y = bounds.getMinY();

        contextMenu.show(view, x, y);
    }

    public void hideContextMenu() {
        contextMenu.hide();
    }

    public void toggleContextMenu() {
        if (contextMenu.isShowing()) {
            hideContextMenu();
        } else {
            showContextMenu();
        }
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public void initDragAndDrop() {

        view.setOnMousePressed(e -> {

            MouseButton button = e.getButton();

            switch (button) {
                case PRIMARY -> {
                    offsetX = e.getX();
                    offsetY = e.getY();
                    view.setCursor(Cursor.MOVE);
                }
                case SECONDARY -> {
                    toggleContextMenu();
                }
                default -> {
                }
            }

        });

        view.setOnMouseDragged(e -> {

            MouseButton button = e.getButton();
            view.toFront();

            switch (button) {
                case PRIMARY -> {
                    view.setCursor(Cursor.MOVE);
                    view.moveRectangle((float) (e.getX() - offsetX), (float) (e.getY() - offsetY));
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
                    float xInView = (float) (localPoint.getX() - offsetX);
                    float yInView = (float) (localPoint.getY() - offsetY);

                    offsetX = 0D;
                    offsetY = 0D;

                    onMouseReleased(xInView, yInView);
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
