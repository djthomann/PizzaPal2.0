package pizzapal.model.storage;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

public class StorageControllerTest {

    private Storage storage;

    private StorageController controller;

    @BeforeEach
    public void init() {
        storage = new Storage(6f, 3f);

        controller = new StorageController(storage);
    }

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }

    @Test
    public void testMoveSupport() {
        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);

        assertEquals(1f, support1.getPosX());

    }

    @Test
    public void testAddSupportFromConstructor() {
        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);

        assertFalse(storage.isEmpty());
        assertEquals(support1, storage.getSupports().get(0));
    }

    @Test
    public void testAddSupportByPlacing() {
        Support support1 = new Support(0.2f, 3f, 1f, 0f);

        support1.putInStorage(storage);

        assertFalse(storage.isEmpty());
        assertEquals(support1, storage.getSupports().get(0));
    }

    @Test
    public void testAddBoardByPlacing() {
        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);
        Support support2 = new Support(storage, 0.2f, 3f, 3f, 0f);

        assertTrue(support1.getBoardsRight().isEmpty());
        assertTrue(support2.getBoardsLeft().isEmpty());

        controller.addBoard(0.2f, Color.BROWN, 2.5f, 1f);

        assertFalse(support1.getBoardsRight().isEmpty());
        assertFalse(support2.getBoardsLeft().isEmpty());

        Board board = support1.getBoardsRight().get(0);

        assertEquals(support1, board.getSupportLeft());
        assertEquals(support2, board.getSupportRight());

    }

    @Test
    public void testMoveBoardBetweenSupports() {

        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);
        Support support2 = new Support(storage, 0.2f, 3f, 3f, 0f);

        Board board = new Board(support1, support2, 0.2f, 0.5f);

        // TODO: Eigentlich insert Board Test?
        assertEquals(2.5f, board.getPosY());
        assertEquals(0.5f, board.getOffsetY());

        controller.move(board, 2f, 1f);

        assertEquals(1f, board.getPosY());
        assertEquals(2f, board.getOffsetY());

        controller.move(board, 2f, 2f);

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

    @Test
    public void testMoveSingleItemBoardChange() {

        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);
        Support support2 = new Support(storage, 0.2f, 3f, 3f, 0f);

        Board board1 = new Board(support1, support2, 0.2f, 0.5f);
        Board board2 = new Board(support1, support2, 0.2f, 1f);

        Item item1 = new Item(board1, null, 0.2f, 0.2f, 0.2f, 0.2f);

        assertTrue(controller.move(item1, 2, 3));
        assertEquals(board1, item1.getBoard());

        assertTrue(controller.move(item1, 2, 2.1f));
        assertEquals(board2, item1.getBoard());
        assertTrue(board1.getItems().isEmpty());

    }

    @Test
    public void testMoveSingleItemListenersChanged() {

        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);
        Support support2 = new Support(storage, 0.2f, 3f, 3f, 0f);

        Board board1 = new Board(support1, support2, 0.2f, 0.5f);
        Board board2 = new Board(support1, support2, 0.2f, 1f);

        Item item1 = new Item(board1, null, 0.2f, 0.2f, 0.2f, 0.2f);

        assertTrue(controller.move(item1, 2, 2.2f));
        assertTrue(board2.containsListener(item1.getListener()));
        assertFalse(board1.containsListener(item1.getListener()));

    }

    @Test
    public void testMoveSingleItemPositionY() {
        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);
        Support support2 = new Support(storage, 0.2f, 3f, 3f, 0f);

        Board board1 = new Board(support1, support2, 0.2f, 0f);
        Board board2 = new Board(support1, support2, 0.2f, 1f);

        Item item1 = new Item(board1, null, 0.2f, 0.2f, 0.2f, 0.2f);

        assertEquals(3.2f, item1.getPosY());

        controller.move(item1, 2f, 2.1f);

        assertEquals(2.2f, item1.getPosY());

    }

    @Test
    public void testMoveBoardItemPositionY() {
        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);
        Support support2 = new Support(storage, 0.2f, 3f, 3f, 0f);

        Board board1 = new Board(support1, support2, 0.2f, 0f);

        Item item1 = new Item(board1, null, 0.2f, 0.2f, 0.2f, 0.2f);

        assertEquals(3.2f, item1.getPosY());
        assertTrue(controller.move(board1, 2f, 2f));
        assertEquals(2.2f, item1.getPosY());
        assertTrue(controller.move(board1, 2f, 1f));
        assertEquals(1.2f, item1.getPosY());
    }

    @Test
    public void testMoveBoardAfterItemLeft() {
        Support support1 = new Support(storage, 0.2f, 3f, 1f, 0f);
        Support support2 = new Support(storage, 0.2f, 3f, 3f, 0f);

        Board board1 = new Board(support1, support2, 0.2f, 0.5f);
        Board board2 = new Board(support1, support2, 0.2f, 1f);

        Item item1 = new Item(board1, null, 0.2f, 0.2f, 0.2f, 0.2f);

        assertTrue(controller.move(item1, 2, 2.2f));
        assertEquals(board2.getPosY() + item1.getHeight(), item1.getPosY());

        assertTrue(controller.move(board1, 2f, 1f));
        assertEquals(board2.getPosY() + item1.getHeight(), item1.getPosY());
    }

}
