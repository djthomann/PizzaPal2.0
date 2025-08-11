package pizzapal.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import javafx.scene.paint.Color;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;

public class ItemTest {

    private Storage storage;
    private StorageController controller;

    @BeforeEach
    public void init() {
        storage = new Storage(10f, 5f);
        controller = new StorageController(storage);
    }

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
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

        Item item = new Item(board1, Color.YELLOW, 10, 0.5f, 0.5f, 0);

        assertEquals(board1, item.getBoard());

        assertTrue(controller.moveItem(item, 4.5f, 5f));

        assertEquals(board2, item.getBoard());
        assertTrue(board1.getItems().isEmpty());
        assertEquals(1, board2.getItems().size());

    }

}
