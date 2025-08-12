package pizzapal.ui.view.entities.board;

import javafx.scene.paint.Color;
import pizzapal.model.domain.entities.Board;
import pizzapal.ui.view.entities.EntityView;
import pizzapal.utils.Helper;

public class BoardView extends EntityView<Board> {

    public BoardView(Color color, float width, float height, float posX, float posY) {
        super(color, width, height, posX, posY);

    }

    public void updateFromModel(Board board) {
        resetRectangle();

        this.setLayoutX(Helper.convertMetersToPixel(board.getPosX()));
        this.setLayoutY(Helper.getPixelPositionYInStorage(board.getSupportLeft().getStorage(), board.getPosY()));

        float newWidth = Helper.convertMetersToPixel(board.getWidth());
        setWidth(newWidth);
    }

    @Override
    public void updateFromModelEdit(Board board) {

        float newHeight = Helper.convertMetersToPixel(board.getHeight());
        Color newColor = board.getColor().getColor();

        this.setPrefHeight(newHeight);

        entityRectangle.setHeight(newHeight);
        entityRectangle.setFill(newColor);

        ghostRectangle.setHeight(newHeight);

    }

}
