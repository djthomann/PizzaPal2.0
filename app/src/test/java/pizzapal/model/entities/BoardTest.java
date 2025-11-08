package pizzapal.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;

public class BoardTest {

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
    public void testBoardConnectionToSupports() {
        Support support1 = new Support(storage, 0.2f, 2f, 2f, 0f);
        Support support2 = new Support(storage, 0.2f, 2f, 3f, 0f);

        Board board = new Board(support1, support2, 0.2f, 0.5f);

        assertEquals(support1, board.getSupportLeft());
        assertEquals(support2, board.getSupportRight());
    }

    @Test
    public void testBoardWidthBetweenSupports() {

        Support support1 = new Support(storage, 0.2f, 2f, 2f, 0f);
        Support support2 = new Support(storage, 0.2f, 2f, 3f, 0f);

        Board board = new Board(support1, support2, 0.2f, 0.5f);

        support1.addBoardRight(board);
        support2.addBoardLeft(board);

        storage.addSupport(support1);
        storage.addSupport(support2);

        assertEquals(0.8f, board.getWidth());
    }

    @Test
    public void testBoardWidthAfterMoving() {

        Support support1 = new Support(storage, 0.2f, 2f, 2f, 0f);
        Support support2 = new Support(storage, 0.2f, 2f, 3f, 0f);

        Support support3 = new Support(storage, 0.3f, 2f, 4f, 0f);
        Support support4 = new Support(storage, 0.2f, 2f, 5f, 0f);

        Board board = new Board(support1, support2, 0.2f, 0.5f);

        support1.addBoardRight(board);
        support2.addBoardLeft(board);

        storage.addSupport(support1);
        storage.addSupport(support2);
        storage.addSupport(support3);
        storage.addSupport(support4);

        controller.move(board, 4.5f, 0.5f);

        assertEquals(0.7f, board.getWidth());

        controller.move(board, 2.5f, 0.5f);

        assertEquals(0.8f, board.getWidth());

    }

    @Test
    public void testBoardConnectionsAfterMove() {

        Support support1 = new Support(storage, 0.2f, 2f, 2f, 0f);
        Support support2 = new Support(storage, 0.2f, 2f, 3f, 0f);

        Support support3 = new Support(storage, 0.3f, 2.5f, 4f, 0f);
        Support support4 = new Support(storage, 0.2f, 2.5f, 5f, 0f);

        Board board = new Board(support1, support2, 0.2f, 0.5f);

        support1.addBoardRight(board);
        support2.addBoardLeft(board);

        storage.addSupport(support1);
        storage.addSupport(support2);
        storage.addSupport(support3);
        storage.addSupport(support4);

        controller.move(board, 4.5f, 0.5f);

        assertEquals(support3, board.getSupportLeft());
        assertEquals(support4, board.getSupportRight());

        controller.move(board, 2.5f, 0.5f);

        assertEquals(support1, board.getSupportLeft());
        assertEquals(support2, board.getSupportRight());

    }

}
