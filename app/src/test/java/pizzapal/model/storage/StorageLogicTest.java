package pizzapal.model.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StorageLogicTest {

    private static StorageLogic logic;

    private static Storage storage;

    @BeforeAll
    public static void init() {
        storage = new Storage(6f, 3f);

        logic = new StorageLogic(storage);
    }

    @Test
    public void positionOutsideStorage() {

        assertFalse(logic.positionInStorage(-1, -1));

    }

}
