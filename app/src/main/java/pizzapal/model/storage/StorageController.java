package pizzapal.model.storage;

import java.util.ArrayDeque;
import java.util.Deque;

import pizzapal.model.entities.Board;
import pizzapal.model.entities.Support;
import pizzapal.model.storage.commands.Command;
import pizzapal.model.storage.commands.MoveBoardCommand;
import pizzapal.model.storage.commands.MoveSupportCommand;

public class StorageController {

    private final Storage storage;

    private final StorageLogic logic;

    private final Deque<Command> redoStack = new ArrayDeque<>();
    private final Deque<Command> undoStack = new ArrayDeque<>();

    public StorageController(Storage storage) {
        this.storage = storage;
        logic = new StorageLogic(storage);
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

    public boolean moveSupport(Support support, float posX, float posY) {
        if (logic.placeSupportPossible(support, posX, posY)) {
            MoveSupportCommand moveCommand = new MoveSupportCommand(support, posX, posY);
            moveCommand.execute();
            undoStack.push(moveCommand);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveBoard(Board board, float posX, float posY) {
        if (logic.moveBoardPossible(board, posX, posY)) {
            MoveBoardCommand moveCommand = new MoveBoardCommand(board, getSupportLeftOfPos(posX),
                    getSupportRightToPos(posX), posY);
            moveCommand.execute();
            undoStack.push(moveCommand);
            System.out.println("POSY: " + posY);
            // board.move(getSupportLeftOfPos(posX), getSupportRightToPos(posX), posY);
            return true;
        } else {
            return false;
        }
    }

    public Support getSupportLeftOfPos(float posX) {

        Support supportLeft = null;

        for (Support support : storage.getSupports()) {
            if (support.getPositionX() < posX) {
                if (supportLeft == null) {
                    supportLeft = support;
                } else if (supportLeft.getPositionX() < support.getPositionX()) {
                    supportLeft = support;
                }
            }
        }

        return supportLeft;

    }

    public Support getSupportRightToPos(float posX) {

        Support supportRight = null;

        for (Support support : storage.getSupports()) {
            if (support.getPositionX() > posX) {
                if (supportRight == null) {
                    supportRight = support;
                } else if (supportRight.getPositionX() > support.getPositionX()) {
                    supportRight = support;
                }
            }
        }

        return supportRight;

    }

}
