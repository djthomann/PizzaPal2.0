package pizzapal.model.storage;

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

    public boolean placeSupportPossible(Support support, float posX, float posY) {

        if (!positionInStorage(posX, posY)) {
            return false;
        }

        System.out.println(
                "POS:" + posX + "|" + posY + "NUM:" + storage.getSupports().size() + storage.getSupports().toString());

        for (Support s : storage.getSupports()) {
            if (s.getPositionX() < posX && s.getPositionX() + Helper.convertMetersToPixel(s.getWidth()) > posX) {
                System.out.println("Stützen durfen nicht ineinander stehen");
                return false;
            }
        }

        return true;

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
        return storage.getSupports().isEmpty();
    }

}
