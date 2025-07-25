package pizzapal.ui.view.entities.board;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pizzapal.Helper;
import pizzapal.model.domain.entities.Board;

public class BoardView extends Pane {

    private final Rectangle boardRectangle;

    private final Rectangle ghostRectangle;

    public BoardView(float width, float height, float posX, float posY) {

        setPrefHeight(height);

        boardRectangle = new Rectangle(200, height);
        boardRectangle.setFill(Color.GREEN);

        ghostRectangle = new Rectangle(200, height);
        ghostRectangle.setFill(Color.DARKGRAY);

        setLayoutX(posX);
        setLayoutY(posY);

        this.getChildren().addAll(ghostRectangle, boardRectangle);

    }

    public void moveRectangle(float posX, float posY) {
        boardRectangle.setLayoutX(posX);
        boardRectangle.setLayoutY(posY);
    }

    public void setWidth(float width) {
        super.setWidth(width);
        boardRectangle.setWidth(width);
        ghostRectangle.setWidth(width);
    }

    public void move(float posX, float posY) {
        boardRectangle.setLayoutX(0);
        boardRectangle.setLayoutY(0);

        ghostRectangle.setLayoutX(0);
        ghostRectangle.setLayoutY(0);

        setLayoutX(posX);
        setLayoutY(posY);
    }

    public void updateFromModel(Board board) {
        resetRectangle();

        this.setLayoutX(Helper.convertMetersToPixel(board.getPosX()));
        this.setLayoutY(Helper.getPixelPositionYInStorage(board.getSupportLeft().getStorage(), board.getPosY()));

        float newWidth = Helper.convertMetersToPixel(board.getWidth());
        setWidth(newWidth);
    }

    public void resetRectangle() {
        boardRectangle.setLayoutX(0);
        boardRectangle.setLayoutY(0);
    }

}
