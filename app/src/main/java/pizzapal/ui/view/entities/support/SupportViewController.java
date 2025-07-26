package pizzapal.ui.view.entities.support;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import pizzapal.Helper;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.UIConfig;
import pizzapal.ui.view.entities.ViewController;

public class SupportViewController implements ViewController {

    private final Support support;

    private final StorageController storageController;

    private final SupportView view;

    private final ContextMenu contextMenu;

    public SupportViewController(StorageController storageController, Support support) {

        this.storageController = storageController;
        this.support = support;

        float widthPx = Helper.convertMetersToPixel(support.getWidth());
        float heightPx = Helper.convertMetersToPixel(support.getHeight());
        float posX = Helper.convertMetersToPixel(support.getPositionX());
        float posY = Helper.getPixelPositionYInStorage(support.getStorage(), support);

        view = new SupportView(widthPx, heightPx, posX, posY);

        contextMenu = new ContextMenu();
        initContextMenu();

        initDragAndDrop();

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
            storageController.delete(support);
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
                    toggleContextMenu();
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
                    hideContextMenu();
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
                    System.out.println(yInView);

                    if (!storageController.moveSupport(support, Helper.convertPixelToMeters(xInView),
                            Helper.convertPixelToMeters(yInView))) {
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
