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
import pizzapal.model.domain.entities.Entity;
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

    public void delete(Entity e) {
        if (e instanceof Item item) {
            delete(item);
        } else if (e instanceof Board board) {
            delete(board);
        } else if (e instanceof Support support) {
            delete(support);
        }

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

    public void addSupport(float width, float height, Color color, float posX, float posY) {
        Support support = new Support(storage, color, width, height, posX, posY);
        storage.addSupport(support);
        notifySupportCreationListeners(support);
    }

    public boolean delete(Support support) {
        if (support.getBoardsLeft().isEmpty() && support.getBoardsRight().isEmpty()) {
            storage.removeSupport(support);
            support.setStorage(null);
            support.delete();
            return true;
        } else {
            NotificationManager.getInstance().addNotification("Can't delete Support");
            return false;
        }

    }

    public boolean moveBoard(Board board, float posX, float posY) {

        if (!logic.positionInStorage(posX, posY)) {
            NotificationManager.getInstance().addNotification("Not In Storage");
            return false;
        }

        if (logic.storageIsEmpty()) {
            NotificationManager.getInstance().addNotification("Storage is Empty");
            return false;
        }

        if (!service.isPositionBetweenTwoSupports(posX)) {
            NotificationManager.getInstance().addNotification("Not between two supports");
            return false;
        }

        Support left = service.getSupportLeftOfPos(posX);
        float offsetY = left.getHeight() - posY;

        if (!logic.supportHasSpaceForBoardRight(left, board, offsetY)) {
            NotificationManager.getInstance().addNotification("No space for board on support");
            return false;
        }

        if (!logic.enoughSpaceForItemsLeftAbove(left, board, offsetY)) {
            NotificationManager.getInstance().addNotification("Not enough space for items");
            return false;
        }

        if (!logic.enoughSpaceForItemsLeftBelow(left, board, offsetY)) {
            NotificationManager.getInstance().addNotification("Not enough space for items");
            return false;
        }

        // snapping to top of support
        if (offsetY < 0) {
            offsetY = 0;
        }

        MoveBoardCommand moveCommand = new MoveBoardCommand(board, service.getSupportLeftOfPos(posX),
                service.getSupportRightOfPos(posX), offsetY);
        moveCommand.execute();
        undoStack.push(moveCommand);

        return true;

    }

    public void addBoard(float height, Color color, float posX, float posY) {

        float offsetY = service.getSupportLeftOfPos(posX).getHeight() - posY;
        if (offsetY < 0) {
            offsetY = 0;
        }

        Board board = new Board(service.getSupportLeftOfPos(posX), service.getSupportRightOfPos(posX), height, offsetY,
                color);
        storage.addBoard(board);
        notifyBoardCreationListeners(board);
    }

    public boolean delete(Board board) {

        Support left = board.getSupportLeft();
        Support right = board.getSupportRight();

        left.getBoardsRight().remove(board);
        right.getBoardsLeft().remove(board);

        board.delete();

        return true;
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

        Board board = service.getBoardBelow(boards, posY);
        if (board == null) {
            NotificationManager.getInstance().addNotification("No Board below");
            return false;
        }

        float offsetX = posX - board.getPosX();
        if (offsetX < 0) {
            offsetX = 0;
        } else if (offsetX > board.getWidth() - item.getWidth()) {
            System.out.println("OFFSET :" + offsetX);
            System.out.println("BAORDW:" + board.getWidth());
            System.out.println("ITEMW" + item.getWeight());
            offsetX = board.getWidth() - item.getWidth();
        }

        item.move(board, offsetX);
        return true;
    }

    public void addItem(float width, float height, float weight, float posX, float posY) {

        Board board = service.getBoardAt(posX);

        if (board == null) {
            NotificationManager.getInstance().addNotification("Couldn't place Item. No Board found.");
        } else {
            float offsetX = posX - board.getPosX();
            Item item = new Item(board, Color.DARKBLUE, weight, width, height, offsetX);
            notifyItemCreationListeners(item);
        }

    }

    public void delete(Item item) {
        System.out.println("Deleting item");
        // Do nothing
    }

}
