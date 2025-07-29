package pizzapal.ui.view.entities.support;

import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.UIConfig;
import pizzapal.ui.view.entities.EntityViewController;
import pizzapal.utils.Helper;

public class SupportViewController extends EntityViewController<Support> {

    private final Support support;

    private final StorageController storageController;

    private final ContextMenu contextMenu;

    public SupportViewController(StorageController storageController, Support support) {
        super(new SupportView(support.getColor(), Helper.convertMetersToPixel(support.getWidth()),
                Helper.convertMetersToPixel(support.getHeight()), Helper.convertMetersToPixel(support.getPosX()),
                Helper.getPixelPositionYInStorage(support.getStorage(), support)));
        this.storageController = storageController;
        this.support = support;

        contextMenu = new ContextMenu();
        initContextMenu();

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

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    @Override
    protected void onMouseReleased(float xInView, float yInView) {
        if (!storageController.moveSupport(support, Helper.convertPixelToMeters(xInView),
                Helper.convertPixelToMeters(yInView))) {
            view.resetRectangle();
        }
    }

}
