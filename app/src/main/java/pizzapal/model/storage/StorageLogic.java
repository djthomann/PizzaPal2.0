package pizzapal.model.storage;

import java.util.List;

import pizzapal.Helper;
import pizzapal.model.entities.Board;
import pizzapal.model.entities.Support;

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
            System.out.println("Moving through support");
            return false;
        } else {
            System.out.println("Not moving through support");
        }

        for (Support s : storage.getSupports()) {
            if (s.getPositionX() < posX && s.getPositionX() + Helper.convertMetersToPixel(s.getWidth()) > posX) {
                System.out.println("Stützen durfen nicht ineinander stehen");
                return false;
            }
        }

        return true;

    }

    public boolean movingThroughSupport(boolean movingRight, Support support, float posX) {

        if (movingRight) {
            System.out.println("MOVING RIGHT");
            List<Board> boards = support.getBoardsRight();
            if (boards.isEmpty()) {
                System.out.println("IS EMPTY");
                return false;
            }
            Support right = boards.get(0).getSupportRight();
            if (right == null) {
                // Shouldn't be possible
                System.out.println("Shouldn't be possible");
                return false;
            } else {
                System.out.println("POSX" + posX);
                System.out.println("STÜTZE" + right.getPositionX());
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
