package pizzapal.ui.view.entities.board;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pizzapal.model.domain.entities.Board;
import pizzapal.ui.view.entities.EntityView;
import pizzapal.utils.Helper;

public class BoardView extends EntityView<Board> {

    private final Rectangle boardRectangle;

    private final Rectangle ghostRectangle;

    public BoardView(Color color, float width, float height, float posX, float posY) {

        boardRectangle = new Rectangle(200, height);
        boardRectangle.setFill(color);

        ghostRectangle = new Rectangle(200, height);
        ghostRectangle.setFill(Color.DARKGRAY);

        setWidth(width);
        setPrefHeight(height);

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
