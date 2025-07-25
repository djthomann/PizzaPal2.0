package pizzapal.model.repository;

import javafx.scene.paint.Color;
import pizzapal.model.entities.Board;
import pizzapal.model.entities.Item;
import pizzapal.model.entities.Support;
import pizzapal.model.storage.Storage;

public class StorageRepository {

    private static StorageRepository instance;

    private StorageRepository() {

    }

    public static StorageRepository getInstance() {
        if (instance == null) {
            instance = new StorageRepository();
        }
        return instance;
    }

    public Storage createStorage() {
        float widthInMeters = 9.0f;
        float heightInMeters = 5f;

        Storage storage = new Storage(widthInMeters, heightInMeters);
        Support support1 = new Support(storage, 0.2f, 2f, 1f, 0f);
        Support support2 = new Support(storage, 0.2f, 2f, 2f, 0f);

        Support support3 = new Support(storage, 0.2f, 2f, 4f, 0f);
        Support support4 = new Support(storage, 0.2f, 2f, 5f, 0f);

        Board board1 = new Board(support1, support2, 0.3f, 0.2f);

        Item item1 = new Item(board1, Color.YELLOW, 1, 0.2f, 0.2f, 0.3f);

        board1.addItem(item1);

        storage.addSupport(support1);
        storage.addSupport(support2);
        storage.addSupport(support3);
        storage.addSupport(support4);

        storage.addBoard(board1);

        return storage;
    }

}
