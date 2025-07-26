package pizzapal.model.storage;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;
import pizzapal.model.service.StorageLogic;

public class StorageLogicTest {

    private StorageLogic logic;

    private Storage storage;

    @BeforeEach
    public void init() {
        storage = new Storage(6f, 3f);

        logic = new StorageLogic(storage);
    }

    @Test
    public void testPositionOutsideStorage() {
        assertFalse(logic.positionInStorage(-1, 2));
        assertFalse(logic.positionInStorage(1, -2));
        assertFalse(logic.positionInStorage(7, 2));
        assertFalse(logic.positionInStorage(4, 5));
    }

    @Test
    public void testPositionInsideStorage() {
        assertTrue(logic.positionInStorage(3f, 1.5f));
        assertTrue(logic.positionInStorage(6f, 3f));
    }

    @Test
    public void testPositionInsideSupport() {
        Support support1 = new Support(storage, 0.2f, 2f, 2f, 0f);

        assertTrue(logic.positionInSupport(2.0f));
        assertTrue(logic.positionInSupport(2.1f));
        assertTrue(logic.positionInSupport(2.2f));
        assertFalse(logic.positionInSupport(0.5f));
        assertFalse(logic.positionInSupport(2.5f));
    }

    @Test
    public void cantMakeBoardNegativeWidthMovingRight() {

        Support support1 = new Support(storage, 0.2f, 2f, 2f, 0f);
        Support support2 = new Support(storage, 0.2f, 2f, 3f, 0f);

        Board board = new Board(support1, support2, 0.2f, 0.5f);

        // TODO
    }

}
