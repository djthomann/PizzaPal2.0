package pizzapal.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.paint.Color;
import pizzapal.model.storage.Storage;
import pizzapal.model.storage.StorageController;

public class ItemTest {

    private Storage storage;
    private StorageController controller;

    @BeforeEach
    public void init() {
        storage = new Storage(10f, 5f);
        controller = new StorageController(storage);
    }

    @Test
    public void testCreateItem() {

        Support support1 = new Support(storage, 0.2f, 2f, 2f, 0f);
        Support support2 = new Support(storage, 0.2f, 2f, 3f, 0f);

        Board board = new Board(support1, support2, 0.2f, 0.5f);

        Item item = new Item(board, Color.YELLOW, 10, 0.5f, 0.5f, 0.2f);

        assertEquals(board, item.getBoard());
    }

    @Test
    public void testBaseConnectionAfterMoving() {

        Support support1 = new Support(storage, 0.2f, 2f, 2f, 0f);
        Support support2 = new Support(storage, 0.2f, 2f, 3f, 0f);

        Support support3 = new Support(storage, 0.3f, 2f, 4f, 0f);
        Support support4 = new Support(storage, 0.2f, 2f, 5f, 0f);

        Board board1 = new Board(support1, support2, 0.2f, 0.5f);
        Board board2 = new Board(support3, support4, 0.2f, 0.5f);

        support1.addBoardRight(board1);
        support2.addBoardLeft(board1);

        support3.addBoardRight(board2);
        support4.addBoardLeft(board2);

        storage.addSupport(support1);
        storage.addSupport(support2);
        storage.addSupport(support3);
        storage.addSupport(support4);

        Item item = new Item(board1, Color.YELLOW, 10, 0.5f, 0.5f, 0);

        assertEquals(board1, item.getBoard());

        controller.moveItem(item, 4.5f, 0.6f);

        assertEquals(board2, item.getBoard());
        assertTrue(board1.getItems().isEmpty());
        assertEquals(1, board2.getItems().size());

    }

}
