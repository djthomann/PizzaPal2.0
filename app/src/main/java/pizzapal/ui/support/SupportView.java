package pizzapal.ui.support;

import javafx.geometry.Bounds;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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

    public void initContextMenu() {
        MenuItem item1 = new MenuItem("Option 1");
        item1.setOnAction(e -> System.out.println("Option 1 geklickt"));

        MenuItem item2 = new MenuItem("Option 2");
        item2.setOnAction(e -> System.out.println("Option 2 geklickt"));

        contextMenu.getItems().addAll(item1, item2);
    }

    public void showContextMenu() {

        Bounds bounds = this.localToScreen(this.getBoundsInLocal());

        double x = bounds.getMinX();
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

    public Rectangle getSupprtRectangle() {
        return supportRectangle;
    }

}
