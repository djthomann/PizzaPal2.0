package pizzapal.model.storage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StorageControllerTest {

    private static Storage storage;

    private static StorageController controller;

    @BeforeAll
    public static void init() {
        storage = new Storage(6f, 3f);

        controller = new StorageController(storage);
    }

    @Test
    public void testSomething() {

    }

}
