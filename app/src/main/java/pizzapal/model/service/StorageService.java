package pizzapal.model.service;

import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Support;

public class StorageService {

    private Storage storage;

    public StorageService(Storage storage) {
        this.storage = storage;
    }

    public Support getSupportLeftOfPos(float posX) {
        Support left = null;

        for (Support s : storage.getSupports()) {
            if (s.getPositionX() < posX) {
                if (left == null || s.getPositionX() > left.getPositionX()) {
                    left = s;
                }
            }
        }

        return left;
    }

    public Support getSupportRightOfPos(float posX) {
        Support right = null;

        for (Support s : storage.getSupports()) {
            if (s.getPositionX() > posX) {
                if (right == null || s.getPositionX() < right.getPositionX()) {
                    right = s;
                }
            }
        }

        return right;
    }

}
