package pizzapal.ui.view.entities.board;

import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Board;
import pizzapal.ui.view.entities.EntityViewController;
import pizzapal.utils.Helper;
import pizzapal.utils.ToolState;

public class BoardViewController extends EntityViewController<Board> {

    public BoardViewController(StorageController storageController, ToolState toolState, Board board) {
        super(storageController, toolState, board, new BoardView(board.getColor().getColor(),
                Helper.convertMetersToPixel(board.getWidth()),
                Helper.convertMetersToPixel(board.getHeight()), Helper
                        .convertMetersToPixel(board.getSupportLeft().getPosX() + board.getSupportLeft().getWidth()),
                Helper.getPixelPositionYInStorage(storageController.getStorage(), board.getPosY())));
    }

}
