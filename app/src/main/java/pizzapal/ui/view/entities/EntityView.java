package pizzapal.ui.view.entities;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pizzapal.model.domain.entities.Entity;
import pizzapal.ui.UIConfig;

public abstract class EntityView<E extends Entity> extends Pane {

    protected Rectangle entityRectangle;

    protected Rectangle ghostRectangle;

    protected EntityView(Color color, float width, float height, float posX, float posY) {

        this.setPrefSize(width, height);
        this.setBackground(UIConfig.BUTTON_HOVER);

        /*
         * DropShadow shadow = new DropShadow();
         * shadow.setRadius(10);
         * shadow.setOffsetX(5);
         * shadow.setOffsetY(5);
         * shadow.setColor(Color.color(0, 0, 0, 0.4));
         */

        entityRectangle = new Rectangle(width, height);
        entityRectangle.setFill(color);

        ghostRectangle = new Rectangle(width, height);
        ghostRectangle.setFill(Color.DARKGRAY);
        // ghostRectangle.setEffect(shadow);

        setLayoutX(posX);
        setLayoutY(posY);

        this.getChildren().addAll(ghostRectangle, entityRectangle);

    }

    public void setHeight(float height) {
        this.setPrefHeight(height);
        entityRectangle.setHeight(height);
        ghostRectangle.setHeight(height);
    }

    public void setWidth(float width) {
        this.setPrefWidth(width);
        entityRectangle.setWidth(width);
        ghostRectangle.setWidth(width);
    }

    public void move(float posX, float posY) {
        entityRectangle.setLayoutX(0);
        entityRectangle.setLayoutY(0);

        ghostRectangle.setLayoutX(0);
        ghostRectangle.setLayoutY(0);

        setLayoutX(posX);
        setLayoutY(posY);
    }

    public void moveRectangle(float posX, float posY) {
        entityRectangle.setLayoutX(posX);
        entityRectangle.setLayoutY(posY);
    }

    public void resetRectangle() {
        entityRectangle.setLayoutX(0);
        entityRectangle.setLayoutY(0);
    }

    protected Rectangle getEntityRectangle() {
        return entityRectangle;
    }

    public Color getColor() {
        return (Color) entityRectangle.getFill();
    }

}
