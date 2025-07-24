package pizzapal.model.storage;

import pizzapal.Helper;
import pizzapal.model.Support;

public class StorageLogic {

    private Storage storage;

    public StorageLogic(Storage storage) {
        this.storage = storage;
    }

    public boolean positionInStorage(float posX, float posY) {

        float storageWidth = Helper.convertMetersToPixel(storage.getWidth());
        float storageHeight = Helper.convertMetersToPixel(storage.getHeight());

        return posX >= 0 && posY >= 0 && posX < storageWidth && posY < storageHeight;
    }

    public boolean placeSupportPossible(Support support, float posX, float posY) {

        if (!positionInStorage(posX, posY)) {
            System.out.println("Nicht im Lager");
            return false;
        }

        for (Support s : storage.getSupports()) {
            if (s.getPositionX() < posX && s.getPositionX() + s.getWidth() > posX) {
                return false;
            }
        }

        return true;

    }

    public boolean storageIsEmpty() {
        return storage.getSupports().isEmpty();
    }

}
