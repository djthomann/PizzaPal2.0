package pizzapal.ui.view.entities.support;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.UIConfig;
import pizzapal.utils.Helper;

public class SupportView extends Pane {

    private final Rectangle supportRectangle;

    private final Rectangle ghostRectangle;

    public SupportView(float width, float height, float posX, float posY) {

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

        this.setLayoutX(Helper.convertMetersToPixel(support.getPositionX()));
        this.setLayoutY(Helper.getPixelPositionYInStorage(support.getStorage(), support.getHeight()));
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
