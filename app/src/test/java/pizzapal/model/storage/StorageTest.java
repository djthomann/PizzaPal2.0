package pizzapal.model.storage;

import org.junit.jupiter.api.Test;

import pizzapal.model.entities.Support;
import pizzapal.model.storage.Storage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

public class StorageTest {

    private static Storage storage;

    @BeforeAll
    public static void createStorage() {
        storage = new Storage(6f, 3f);
    }

    @Test
    public void testStorageAddAndRemoveSupport() {

        assertTrue(storage.isEmpty());

        Support support = new Support(storage, 0.2f, 1f, 1f, 0);

        storage.addSupport(support);
        assertTrue(storage.getSupports().size() == 1);

        storage.removeSupport(support);
        assertTrue(storage.isEmpty());
    }

}
