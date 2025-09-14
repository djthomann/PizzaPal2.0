package pizzapal.model.controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.paint.Color;
import pizzapal.model.commands.Command;
import pizzapal.model.commands.add.AddBoardCommand;
import pizzapal.model.commands.add.AddItemCommand;
import pizzapal.model.commands.add.AddSupportCommand;
import pizzapal.model.commands.delete.DeleteBoardCommand;
import pizzapal.model.commands.delete.DeleteItemCommand;
import pizzapal.model.commands.delete.DeleteSupportCommand;
import pizzapal.model.commands.edit.EditBoardCommand;
import pizzapal.model.commands.edit.EditItemCommand;
import pizzapal.model.commands.edit.EditSupportCommand;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Entity;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.SerializableColor;
import pizzapal.model.domain.entities.Support;
import pizzapal.model.listener.create.BoardCreationListener;
import pizzapal.model.listener.create.ItemCreationListener;
import pizzapal.model.listener.create.SupportCreationListener;
import pizzapal.model.service.StorageLogic;
import pizzapal.model.service.StorageService;

// TODO Refactor add and edit methods even more
public class StorageController {

    private static final Logger logger = LoggerFactory.getLogger(StorageController.class);

    private final Storage storage;

    private final Deque<Command> redoStack = new ArrayDeque<>();
    private final Deque<Command> undoStack = new ArrayDeque<>();

    private final List<SupportCreationListener> supportCreationListeners = new ArrayList<>();
    private final List<BoardCreationListener> boardCreationListeners = new ArrayList<>();
    private final List<ItemCreationListener> itemCreationListeners = new ArrayList<>();

    private final BoardController boardController;
    private final SupportController supportController;
    private final ItemController itemController;

    StorageService service;

    public StorageController(Storage storage) {
        this.storage = storage;
        service = new StorageService(storage);
        StorageLogic logic = new StorageLogic(storage, service);

        boardController = new BoardController(logic, service);
        supportController = new SupportController(logic, service);
        itemController = new ItemController(logic, service);
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            logger.info("Undoing command: " + command.toString());
            command.undo();
            if (command instanceof DeleteSupportCommand deleteSupportCommand) {
                notifySupportCreationListeners(deleteSupportCommand.getSupport());
            } else if (command instanceof DeleteBoardCommand deleteBoardCommand) {
                notifyBoardCreationListeners(deleteBoardCommand.getBoard());
                for (Item i : deleteBoardCommand.getBoard().getItems()) {
                    notifyItemCreationListeners(i);
                }
            } else if (command instanceof DeleteItemCommand deleteItemCommand) {
                notifyItemCreationListeners(deleteItemCommand.getItem());
            }
            redoStack.push(command);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            logger.info("Redoing command: " + command.toString());
            command.execute();
            if (command instanceof AddSupportCommand addSupportCommand) {
                notifySupportCreationListeners(addSupportCommand.getSupport());
            } else if (command instanceof AddBoardCommand addBoardCommand) {
                notifyBoardCreationListeners(addBoardCommand.getBoard());
            } else if (command instanceof AddItemCommand addItemCommand) {
                notifyItemCreationListeners(addItemCommand.getItem());
            }
            undoStack.push(command);
        }
    }

    public Storage getStorage() {
        return storage;
    }

    public void edit(Entity oldEntity, Entity newEntity) {
        logger.info(
                "Editing Entity: " + newEntity.toString());
        if (oldEntity instanceof Item item) {
            editItem(item, newEntity.getWidth(), newEntity.getHeight(), newEntity.getColor().getColor());
        } else if (oldEntity instanceof Board board) {
            editBoard(board, newEntity.getHeight(), newEntity.getColor().getColor());
        } else if (oldEntity instanceof Support support) {
            editSupport(support, newEntity.getWidth(), newEntity.getHeight(), newEntity.getColor().getColor());
        }
    }

    public boolean delete(Entity e) {
        logger.info("Deleting Entity: " + e.toString());
        Command deleteCommand = null;
        if (e instanceof Item item) {
            deleteCommand = itemController.delete(item);
        } else if (e instanceof Board board) {
            deleteCommand = boardController.delete(board);
        } else if (e instanceof Support support) {
            deleteCommand = supportController.delete(support);
        }
        if (deleteCommand != null) {
            deleteCommand.execute();
            undoStack.push(deleteCommand);
            return true;
        } else {
            return false;
        }
    }

    // TODO: return necessary?
    public boolean move(Entity e, float posX, float posY) {
        logger.info("Moving Entity: " + e.toString());
        Command moveCommand = null; // Maybe abstract even more?
        if (e instanceof Item item) {
            moveCommand = itemController.moveItem(item, posX, posY);
        } else if (e instanceof Board board) {
            moveCommand = boardController.moveBoard(board, posX, posY);
        } else if (e instanceof Support support) {
            moveCommand = supportController.moveSupport(support, posX, posY);
        }
        if (moveCommand != null) {
            moveCommand.execute();
            undoStack.push(moveCommand);
            return true;
        } else {
            return false;
        }
    }

    public void addSupport(Support support) {
        addSupport(support.getWidth(), support.getHeight(), support.getColor(), support.getPosX(),
                support.getPosY());
    }

    public void addSupport(float width, float height, SerializableColor color, float posX, float posY) {

        AddSupportCommand addCommand = supportController.addSupport(width, height, color, posX, posY);
        if (addCommand != null) {
            addCommand.execute();
            undoStack.push(addCommand);
            notifySupportCreationListeners(addCommand.getSupport());
        }
    }

    public void editSupport(Support support, float newWidth, float newHeight, Color newColor) {

        EditSupportCommand editCommand = supportController.editSupport(support, newWidth, newHeight, newColor);
        editCommand.execute();
        undoStack.push(editCommand);

    }

    public void addBoard(Board board) {
        addBoard(board.getHeight(), board.getColor(), board.getPosX(), board.getPosY());
    }

    public void addBoard(float height, SerializableColor color, float posX, float posY) {

        AddBoardCommand addCommand = boardController.addBoard(height, color, posX, posY);
        if (addCommand != null) {
            addCommand.execute();
            undoStack.push(addCommand);
            notifyBoardCreationListeners(addCommand.getBoard());
        }
    }

    private void editBoard(Board board, float newHeight, Color newColor) {

        EditBoardCommand editCommand = boardController.editBoard(board, newHeight, newColor);
        editCommand.execute();
        undoStack.push(editCommand);

    }

    public void addItem(Item item) {
        addItem(item.getWidth(), item.getHeight(), item.getWeight(), item.getPosX(), item.getPosY(), item.getColor());
    }

    public void addItem(float width, float height, float weight, float posX, float posY, SerializableColor color) {

        AddItemCommand addCommand = itemController.addItem(width, height, weight, posX, posY, color);
        if (addCommand != null) {
            addCommand.execute();
            undoStack.push(addCommand);
            notifyItemCreationListeners(addCommand.getItem());
        }

    }

    public void editItem(Item item, float newWidth, float newHeight, Color newColor) {

        EditItemCommand editCommand = itemController.editItem(item, newWidth, newHeight, newColor);
        if (editCommand != null) {
            editCommand.execute();
            undoStack.push(editCommand);
        }
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

}
