package pizzapal.model.controller;

import javafx.scene.paint.Color;
import pizzapal.model.commands.add.AddBoardCommand;
import pizzapal.model.commands.edit.EditBoardCommand;
import pizzapal.model.commands.move.MoveBoardCommand;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;
import pizzapal.model.service.StorageLogic;
import pizzapal.model.service.StorageService;
import pizzapal.utils.NotificationManager;

class BoardController extends EntityController<Board> {

    protected BoardController(StorageLogic logic, StorageService service) {
        super(logic, service);
    }

    public MoveBoardCommand moveBoard(Board board, float posX, float posY) {

        if (!logic.positionInStorage(posX, posY)) {
            NotificationManager.getInstance().addNotification("Not In Storage");
            return null;
        }

        if (logic.storageIsEmpty()) {
            NotificationManager.getInstance().addNotification("Storage is Empty");
            return null;
        }

        if (!logic.isPositionBetweenTwoSupports(posX)) {
            NotificationManager.getInstance().addNotification("Not between two supports");
            return null;
        }

        Support left = service.getSupportLeftOfPos(posX);
        float offsetY = left.getHeight() - posY;

        if (!logic.supportHasSpaceForBoardRight(left, board, offsetY)) {
            NotificationManager.getInstance().addNotification("No space for board on support");
            return null;
        }

        if (!logic.enoughSpaceForItemsLeftAbove(left, board, offsetY)) {
            NotificationManager.getInstance().addNotification("Not enough space for items");
            return null;
        }

        if (!logic.enoughSpaceForItemsLeftBelow(left, board, offsetY)) {
            NotificationManager.getInstance().addNotification("Not enough space for items");
            return null;
        }

        // snapping to top of support
        if (offsetY < 0) {
            offsetY = 0;
        }

        MoveBoardCommand moveCommand = new MoveBoardCommand(board, service.getSupportLeftOfPos(posX),
                service.getSupportRightOfPos(posX), offsetY);

        return moveCommand;

    }

    public AddBoardCommand addBoard(float height, Color color, float posX, float posY) {

        if (!logic.isPositionBetweenTwoSupports(posX)) {
            NotificationManager.getInstance().addNotification("Not between two supports");
            return null;
        }

        float offsetY = service.getSupportLeftOfPos(posX).getHeight() - posY;
        if (offsetY < 0) {
            offsetY = 0;
        }

        Support left = service.getSupportLeftOfPos(posX);
        Support right = service.getSupportRightOfPos(posX);

        Board board = new Board(height, offsetY,
                color);

        if (!logic.enoughSpaceForItemsLeftAbove(left, board, offsetY)) {
            NotificationManager.getInstance().addNotification("Not enough space for items");
        }

        if (!logic.enoughSpaceForItemsLeftBelow(left, board, offsetY)) {
            NotificationManager.getInstance().addNotification("Not enough space for items");

        }

        AddBoardCommand addCommand = new AddBoardCommand(board, left,
                right);
        return addCommand;

    }

    protected EditBoardCommand editBoard(Board board, float newHeight, Color newColor) {

        EditBoardCommand editCommand = new EditBoardCommand(board, newHeight, newColor);
        return editCommand;

    }

    public boolean delete(Board board) {
        board.delete();

        return true;
    }

}
