package pizzapal.ui.support;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import pizzapal.ui.UIConfig;

public class SupportView extends Pane {

    private final Rectangle supportRectangle;

    public SupportView(float width, float height, float posX, float posY) {

        this.setPrefSize(width, height);
        this.setBackground(UIConfig.STORAGE_BACKGROUND);

        supportRectangle = new Rectangle(width, height);

        setLayoutX(posX);
        setLayoutY(posY);

        this.getChildren().add(supportRectangle);

    }

    public Rectangle getSupprtRectangle() {
        return supportRectangle;
    }

}
