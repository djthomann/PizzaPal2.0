package pizzapal.model.controller;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import pizzapal.NotificationManager;
import pizzapal.model.commands.Command;
import pizzapal.model.commands.MoveBoardCommand;
import pizzapal.model.commands.MoveSupportCommand;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;
import pizzapal.model.service.StorageLogic;
import pizzapal.model.service.StorageService;

public class StorageController {

    private final Storage storage;

    private final StorageLogic logic;

    private final StorageService service;

    private final Deque<Command> redoStack = new ArrayDeque<>();
    private final Deque<Command> undoStack = new ArrayDeque<>();

    public StorageController(Storage storage) {
        this.storage = storage;
        logic = new StorageLogic(storage);
        service = new StorageService(storage);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }

    public Storage getStorage() {
        return storage;
    }

    public boolean addSupport() {
        return false;
    }

    public boolean moveItem(Item item, float posX, float posY) {

        // TODO Should be done in logic
        Support left = service.getSupportLeftOfPos(posX);
        Support right = service.getSupportRightOfPos(posX);

        if (left == null || right == null)
            return false;

        List<Board> boards = left.getBoardsRight();
        if (boards.isEmpty())
            return false;

        Board board = boards.get(0);
        item.move(board);
        return true;
    }

    public boolean moveSupport(Support support, float posX, float posY) {
        if (logic.placeSupportPossible(support, posX, posY)) {
            MoveSupportCommand moveCommand = new MoveSupportCommand(support, posX, posY);
            moveCommand.execute();
            undoStack.push(moveCommand);
            return true;
        } else {
            NotificationManager.getInstance().addNotification("Support can't be moved");
            return false;
        }
    }

    public boolean delete(Support support) {
        support.delete();
        storage.getSupports().remove(support);
        return true;
    }

    public boolean moveBoard(Board board, float posX, float posY) {
        if (logic.moveBoardPossible(board, posX, posY)) {
            MoveBoardCommand moveCommand = new MoveBoardCommand(board, service.getSupportLeftOfPos(posX),
                    service.getSupportRightOfPos(posX), posY);
            moveCommand.execute();
            undoStack.push(moveCommand);
            // board.move(getSupportLeftOfPos(posX), getSupportRightToPos(posX), posY);
            return true;
        } else {
            return false;
        }
    }

}
