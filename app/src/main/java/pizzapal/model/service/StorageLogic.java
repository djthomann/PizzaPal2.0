package pizzapal.model.service;

import java.util.List;

import pizzapal.Helper;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;

public class StorageLogic {

    private Storage storage;

    public StorageLogic(Storage storage) {
        this.storage = storage;
    }

    public boolean positionInStorage(float posX, float posY) {

        float storageWidth = Helper.convertMetersToPixel(storage.getWidth());
        float storageHeight = Helper.convertMetersToPixel(storage.getHeight());

        // System.out.println("Nicht im Lager");

        return posX >= 0 && posY >= 0 && posX < storageWidth && posY < storageHeight;
    }

    public boolean positionInSupport(float posX) {
        for (Support s : storage.getSupports()) {
            if (s.getPositionX() <= posX && s.getPositionX() + s.getWidth() >= posX) {
                return true;
            }
        }

        return false;
    }

    public boolean placeSupportPossible(Support support, float posX, float posY) {

        if (support.getPositionX() == posX)
            return true;

        if (!positionInStorage(posX, posY)) {
            return false;
        }

        boolean movingRight = support.getPositionX() < posX;
        if (movingThroughSupport(movingRight, support, posX)) {
            return false;
        }

        for (Support s : storage.getSupports()) {
            if (s.getPositionX() < posX && s.getPositionX() + Helper.convertMetersToPixel(s.getWidth()) > posX) {
                return false;
            }
        }

        return true;

    }

    public boolean movingThroughSupport(boolean movingRight, Support support, float posX) {

        if (movingRight) {
            List<Board> boards = support.getBoardsRight();
            if (boards.isEmpty()) {
                return false;
            }
            Support right = boards.get(0).getSupportRight();
            if (right == null) {
                // Shouldn't be possible
                return false;
            } else {
                return right.getPositionX() < posX;
            }
        } else {
            return false;
        }
    }

    public boolean moveBoardPossible(Board board, float posX, float posY) {

        if (!positionInStorage(posX, posY)) {
            return false;
        }

        if (storageIsEmpty()) {
            return false;
        }

        return true;

    }

    public boolean storageIsEmpty() {
        return storage.isEmpty();
    }

}
