package pizzapal.ui.support;

import javafx.geometry.Bounds;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pizzapal.Helper;
import pizzapal.model.entities.Support;
import pizzapal.ui.UIConfig;

public class SupportView extends Pane {

    private final Rectangle supportRectangle;

    private final Rectangle ghostRectangle;

    private final ContextMenu contextMenu;

    public SupportView(float width, float height, float posX, float posY) {

        contextMenu = new ContextMenu();
        initContextMenu();

        this.setPrefSize(width, height);
        this.setBackground(UIConfig.STORAGE_BACKGROUND_DARK);

        supportRectangle = new Rectangle(width, height);
        supportRectangle.setFill(Color.BROWN);

        ghostRectangle = new Rectangle(width, height);
        ghostRectangle.setFill(Color.DARKGRAY);

        setLayoutX(posX);
        setLayoutY(posY);

        this.getChildren().addAll(ghostRectangle, supportRectangle);

    }

    public void updateFromModel(Support support) {

        resetRectangle();

        System.out.println(support.toString());
        System.out.println("Update from model");
        this.setLayoutX(support.getPositionX());
        this.setLayoutY(Helper.getPixelPositionYInStorage(support.getStorage(), support));
    }

    public void initContextMenu() {

        // contextMenu.setStyle("-fx-background-color: black; -fx-border-color: black;
        // -fx-border-width: 1;");

        contextMenu.set

        MenuItem item1 = new MenuItem("Option 1");
        item1.setOnAction(e -> System.out.println("Option 1 geklickt"));

        ImageView icon1 = new ImageView(Helper.loadImage("/icons/edit.png"));
        icon1.setFitWidth(16);
        icon1.setFitHeight(16);
        item1.setGraphic(icon1);
        item1.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");

        MenuItem item2 = new MenuItem("Option 2");
        item2.setOnAction(e -> System.out.println("Option 2 geklickt"));

        ImageView icon2 = new ImageView(Helper.loadImage("/icons/trash.png"));
        icon2.setFitWidth(16);
        icon2.setFitHeight(16);
        item2.setGraphic(icon2);
        item2.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");

        contextMenu.getItems().addAll(item1, item2);
    }

    public void showContextMenu() {

        Bounds bounds = this.localToScreen(this.getBoundsInLocal());

        double x = bounds.getMinX() + supportRectangle.widthProperty().get() + UIConfig.CONTEXT_MENU_OFFSET_PIXEL;
        double y = bounds.getMinY();

        contextMenu.show(this, x, y);
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

    public void moveRectangle(float posX, float posY) {
        supportRectangle.setLayoutX(posX);
        supportRectangle.setLayoutY(posY);
    }

    public void move(float posX, float posY) {
        supportRectangle.setLayoutX(0);
        supportRectangle.setLayoutY(0);

        ghostRectangle.setLayoutX(0);
        ghostRectangle.setLayoutY(0);

        setLayoutX(posX);
        setLayoutY(posY);
    }

    public void resetRectangle() {
        supportRectangle.setLayoutX(0);
        supportRectangle.setLayoutY(0);
    }

    public Rectangle getSupportRectangle() {
        return supportRectangle;
    }

}
