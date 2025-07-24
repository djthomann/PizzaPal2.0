package pizzapal.model.storage;

import pizzapal.model.entities.Board;
import pizzapal.model.entities.Support;

public class StorageController {

    private final Storage storage;

    private final StorageLogic logic;

    public StorageController(Storage storage) {
        this.storage = storage;
        logic = new StorageLogic(storage);
    }

    public Storage getStorage() {
        return storage;
    }

    public boolean moveSupport(Support support, float posX, float posY) {
        if (logic.placeSupportPossible(support, posX, posY)) {
            support.move(posX);
            return true;
        } else {
            return false;
        }
    }

    public boolean moveBoard(Board board, float posX, float posY) {
        if (logic.moveBoardPossible(board, posX, posY)) {
            board.move(getSupportLeftOfPos(posX), getSupportRightToPos(posX), posY);
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
