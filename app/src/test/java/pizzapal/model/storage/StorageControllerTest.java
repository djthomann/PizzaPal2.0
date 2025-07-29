package pizzapal.model.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;

public class StorageControllerTest {

    private Storage storage;

    private StorageController controller;

    @BeforeEach
    public void init() {
        storage = new Storage(6f, 3f);

        controller = new StorageController(storage);
    }

    @Test
    public void testMoveSupport() {
        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);

        assertEquals(1f, support1.getPosX());

    }

    @Test
    public void testMoveBoardBetweenSupports() {

        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);
        Support support2 = new Support(storage, 0.2f, 3f, 3f, 0f);

        Board board = new Board(support1, support2, 0.2f, 0.5f);

        // TODO: Eigentlich insert Board Test?
        assertEquals(2.5f, board.getPosY());
        assertEquals(0.5f, board.getOffsetY());

        controller.moveBoard(board, 2f, 1f);

        assertEquals(1f, board.getPosY());
        assertEquals(2f, board.getOffsetY());

        controller.moveBoard(board, 2f, 2f);

        assertEquals(2f, board.getPosY());
        assertEquals(1f, board.getOffsetY());

    }

    @Test
    public void testDeleteSupportSingle() {
        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);

        controller.delete(support1);

        assertTrue(storage.isEmpty());
    }

    @Test
    public void testDeleteSupportConnectedToBoard() {
        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);
        Support support2 = new Support(storage, 0.2f, 3f, 3f, 0f);

        Board board = new Board(support1, support2, 0.2f, 0.5f);

        assertFalse(controller.delete(support1));
    }

    @Test
    public void testDeleteBoardNoItems() {
        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);
        Support support2 = new Support(storage, 0.2f, 3f, 3f, 0f);

        Board board = new Board(support1, support2, 0.2f, 0.5f);

        assertTrue(controller.delete(board));

        assertTrue(support1.getBoardsRight().isEmpty());
        assertTrue(support2.getBoardsLeft().isEmpty());
    }

    @Test
    public void testDeleteBoardWithItems() {
        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);
        Support support2 = new Support(storage, 0.2f, 3f, 3f, 0f);

        Board board = new Board(support1, support2, 0.2f, 0.5f);

        Item item1 = new Item(board, null, 0.2f, 0.2f, 0.2f, 0.2f);
        Item item2 = new Item(board, null, 0.2f, 0.2f, 0.2f, 0.2f);

        assertTrue(controller.delete(board));

        assertTrue(support1.getBoardsRight().isEmpty());
        assertTrue(support2.getBoardsLeft().isEmpty());

        assertTrue(item1.getBoard() == null);
        assertTrue(item2.getBoard() == null);
    }
}
