package pizzapal.model.storage;

import pizzapal.model.Support;

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

    public boolean moveSupport(Support support, float posY, float posX) {
        if (logic.placeSupportPossible(support, posY, posX)) {
            support.move(posX, posY);
            System.out.println("Support moved");
            return true;
        } else {
            System.out.println("Error: Couldn't place Support");
            return false;
        }
    }

}
