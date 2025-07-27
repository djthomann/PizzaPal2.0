package pizzapal.model.controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javafx.scene.paint.Color;
import pizzapal.model.commands.Command;
import pizzapal.model.commands.MoveBoardCommand;
import pizzapal.model.commands.MoveSupportCommand;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;
import pizzapal.model.service.StorageLogic;
import pizzapal.model.service.StorageService;
import pizzapal.utils.NotificationManager;

public class StorageController {

    private final Storage storage;

    private final StorageLogic logic;

    private final StorageService service;

    private final Deque<Command> redoStack = new ArrayDeque<>();
    private final Deque<Command> undoStack = new ArrayDeque<>();

    private List<SupportCreationListener> supportCreationListeners = new ArrayList<>();
    private List<BoardCreationListener> boardCreationListeners = new ArrayList<>();
    private List<ItemCreationListener> itemCreationListeners = new ArrayList<>();

    public StorageController(Storage storage) {
        this.storage = storage;
        service = new StorageService(storage);
        logic = new StorageLogic(storage, service);
    }

    public void addSupportCreationListener(SupportCreationListener l) {
        supportCreationListeners.add(l);
    }

    public void removeSupportCreationListener(SupportCreationListener l) {
        supportCreationListeners.remove(l);
    }

    public void notifySupportCreationListeners(Support support) {
        for (SupportCreationListener l : supportCreationListeners) {
            l.onSupportCreated(support);
        }
    }

    public void addBoardCreationListener(BoardCreationListener l) {
        boardCreationListeners.add(l);
    }

    public void removeBoardCreationListener(BoardCreationListener l) {
        boardCreationListeners.remove(l);
    }

    public void notifyBoardCreationListeners(Board board) {
        for (BoardCreationListener l : boardCreationListeners) {
            l.onBoardCreate(board);
        }
    }

    public void addItemCreationListener(ItemCreationListener l) {
        itemCreationListeners.add(l);
    }

    public void removeItemCreationListener(ItemCreationListener l) {
        itemCreationListeners.remove(l);
    }

    public void notifyItemCreationListeners(Item item) {
        for (ItemCreationListener l : itemCreationListeners) {
            l.onItemCreate(item);
        }
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

    public void addSupport(float posX, float posY) {
        Support support = new Support(storage, 0.3f, 2f, posX, posY);
        storage.addSupport(support);
        notifySupportCreationListeners(support);
    }

    public boolean delete(Support support) {
        support.delete();
        storage.getSupports().remove(support);
        return true;
    }

    public boolean moveBoard(Board board, float posX, float posY) {
        if (logic.moveBoardPossible(board, posX, posY)) {
            Support left = service.getSupportLeftOfPos(posX);
            float offsetY = left.getHeight() - posY;

            if (offsetY < 0) {
                offsetY = 0;
            }

            MoveBoardCommand moveCommand = new MoveBoardCommand(board, service.getSupportLeftOfPos(posX),
                    service.getSupportRightOfPos(posX), offsetY);
            moveCommand.execute();
            undoStack.push(moveCommand);
            return true;
        } else {
            NotificationManager.getInstance().addNotification("Move Board not possible");
            return false;
        }
    }

    public void addBoard(float posX, float posY) {
        Board board = new Board(service.getSupportLeftOfPos(posX), service.getSupportRightOfPos(posX), posX, 0);
        storage.addBoard(board);
        notifyBoardCreationListeners(board);
    }

    public void addItem(float posX, float posY) {

        Board board = service.getBoardAt(posX);

        if (board == null) {
            NotificationManager.getInstance().addNotification("Couldn't place Item. No Board found.");
        } else {
            Item item = new Item(board, Color.DARKBLUE, 0.2f, 0.2f, 0.2f, 0);
            notifyItemCreationListeners(item);
        }

    }

}
