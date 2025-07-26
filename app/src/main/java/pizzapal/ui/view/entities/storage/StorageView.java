package pizzapal.ui.view.entities.storage;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pizzapal.ui.UIConfig;

public class StorageView extends Pane {

    private Rectangle ghostRectangle;

    public StorageView(float width, float height) {

        this.setBackground(UIConfig.STORAGE_BACKGROUND);

        this.setPrefSize(width, height);

        ghostRectangle = new Rectangle(20, 100);
        ghostRectangle.setFill(Color.BLACK);
        ghostRectangle.setOpacity(0.4);
        ghostRectangle.setVisible(false);

        this.getChildren().add(ghostRectangle);

    }

    public void showGhostRectangle() {
        ghostRectangle.setVisible(true);
    }

    public void hideGhostRectangle() {
        ghostRectangle.setVisible(false);
    }

    public void moveGhostRectangle(float posX, float posY) {
        ghostRectangle.setLayoutX(posX);
        ghostRectangle.setLayoutY(posY);
    }

}
