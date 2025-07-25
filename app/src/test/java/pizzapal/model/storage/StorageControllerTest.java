package pizzapal.model.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageControllerTest {

    private Storage storage;

    private StorageController controller;

    @BeforeEach
    public void init() {
        storage = new Storage(6f, 3f);

        controller = new StorageController(storage);
    }

    @Test
    public void testSomething() {

    }

}
