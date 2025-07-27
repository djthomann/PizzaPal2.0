package pizzapal.model.service;

import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;

public class StorageService {

    private Storage storage;

    public StorageService(Storage storage) {
        this.storage = storage;
    }

    public Support getSupportLeftOfPos(float posX) {
        Support left = null;

        for (Support s : storage.getSupports()) {
            if (s.getPosX() < posX) {
                if (left == null || s.getPosX() > left.getPosX()) {
                    left = s;
                }
            }
        }

        return left;
    }

    public Support getSupportRightOfPos(float posX) {
        Support right = null;

        for (Support s : storage.getSupports()) {
            if (s.getPosX() > posX) {
                if (right == null || s.getPosX() < right.getPosX()) {
                    right = s;
                }
            }
        }

        return right;
    }

    public boolean isPositionBetweenTwoSupports(float posX) {
        Support left = getSupportLeftOfPos(posX);
        Support right = getSupportRightOfPos(posX);

        return left != null && right != null;
    }

    public Board getBoardAt(float posX) {
        Support left = getSupportLeftOfPos(posX);
        Support right = getSupportRightOfPos(posX);

        if (left == null) {
            return null;
        }

        if (right == null) {
            return null;
        }

        return left.getBoardsRight().get(0);
    }

}
