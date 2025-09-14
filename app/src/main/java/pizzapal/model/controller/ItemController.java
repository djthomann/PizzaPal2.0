package pizzapal.model.controller;

import java.util.List;

import javafx.scene.paint.Color;
import pizzapal.model.commands.add.AddItemCommand;
import pizzapal.model.commands.delete.DeleteItemCommand;
import pizzapal.model.commands.edit.EditItemCommand;
import pizzapal.model.commands.move.MoveItemCommand;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.SerializableColor;
import pizzapal.model.domain.entities.Support;
import pizzapal.model.service.StorageLogic;
import pizzapal.model.service.StorageService;
import pizzapal.utils.NotificationManager;

public class ItemController extends EntityController<Item> {

    public ItemController(StorageLogic logic, StorageService service) {
        super(logic, service);
    }

    public MoveItemCommand moveItem(Item item, float posX, float posY) {

        if (!logic.isPositionBetweenTwoSupports(posX)) {
            NotificationManager.getInstance().addNotification("Not between two supports");
            return null;
        }

        Support left = service.getSupportLeftOfPos(posX);

        List<Board> boards = left.getBoardsRight();
        if (boards.isEmpty()) {

            NotificationManager.getInstance().addNotification("No boards found");
            return null;
        }

        Board board = service.getBoardBelow(boards, posY);
        if (board == null) {
            NotificationManager.getInstance().addNotification("No Board below");
            return null;
        }

        float offsetX = posX - board.getPosX();
        if (offsetX < 0) {
            offsetX = 0;
        } else if (offsetX > board.getWidth() - item.getWidth()) {
            offsetX = board.getWidth() - item.getWidth();
        }

        if (!logic.boardItemsDontCollide(board, item, offsetX)) {
            NotificationManager.getInstance().addNotification("Collides with other item");
            return null;
        }

        if (!logic.boardHasEnoughVerticalSpace(board, item)) {
            NotificationManager.getInstance().addNotification("Not enough vertical space on board");
            return null;
        }

        MoveItemCommand moveCommand = new MoveItemCommand(item, board, offsetX);
        return moveCommand;
    }

    public AddItemCommand addItem(float width, float height, float weight, float posX, float posY,
            SerializableColor color) {

        if (!logic.isPositionBetweenTwoSupports(posX)) {
            NotificationManager.getInstance().addNotification("Not between two supports");
            return null;
        }

        Support left = service.getSupportLeftOfPos(posX);

        List<Board> boards = left.getBoardsRight();
        if (boards.isEmpty()) {
            NotificationManager.getInstance().addNotification("No boards found");
            return null;
        }

        Board board = service.getBoardBelow(posX, posY);

        if (board == null) {
            NotificationManager.getInstance().addNotification("Couldn't place Item. No Board found below");
            return null;
        }

        float offsetX = posX - board.getPosX();
        Item item = new Item(color, weight, width, height, offsetX);
        AddItemCommand addCommand = new AddItemCommand(item, board);
        return addCommand;

    }

    public EditItemCommand editItem(Item item, float newWidth, float newHeight, Color newColor) {

        EditItemCommand editCommand = new EditItemCommand(item, newWidth, newHeight, newColor);
        return editCommand;
    }

    public DeleteItemCommand delete(Item item) {
        return new DeleteItemCommand(item);
    }

}
