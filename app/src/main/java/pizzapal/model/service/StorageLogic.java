package pizzapal.model.service;

import java.util.List;

import pizzapal.NotificationManager;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;

public class StorageLogic {

    private Storage storage;

    public StorageLogic(Storage storage) {
        this.storage = storage;
    }

    public boolean positionInStorage(float posX, float posY) {

        float storageWidth = storage.getWidth();
        float storageHeight = storage.getHeight();

        // System.out.println("Nicht im Lager");

        return posX >= 0 && posY >= 0 && posX <= storageWidth && posY <= storageHeight;
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

        if (support.getPositionX() == posX) {
            NotificationManager.getInstance().addNotification("Is not a movement");
            return true;
        }

        if (!positionInStorage(posX, posY)) {
            NotificationManager.getInstance().addNotification("Not inside Storage");
            return false;
        }

        boolean movingRight = support.getPositionX() < posX;
        if (movingThroughSupport(movingRight, support, posX)) {
            NotificationManager.getInstance().addNotification("Moving through other support");
            return false;
        }

        // Check for collisions
        for (Support s : storage.getSupports()) {
            if (s.getPositionX() < posX && s.getPositionX() + s.getWidth() > posX) {
                // Collision
                NotificationManager.getInstance().addNotification("Collision happened");
                if (s.equals(support)) {
                    NotificationManager.getInstance().addNotification("Is same support");
                    return true;
                } else {
                    NotificationManager.getInstance().addNotification("Different support");
                    return false;
                }

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
