package pizzapal.model.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;
import pizzapal.model.service.StorageService;

public class StorageServiceTest {

    private Storage storage;

    private StorageService service;

    @BeforeEach
    public void init() {
        storage = new Storage(6f, 3f);

        service = new StorageService(storage);
    }

    @Test
    public void testGetSupportLeftOfPos() {
        Support support = new Support(storage, 0.2f, 1f, 1f, 0);

        Support left = service.getSupportLeftOfPos(1.5f);
        assertEquals(support, left);

        Support support2 = new Support(storage, 0.2f, 1f, 0.5f, 0);
        storage.addSupport(support2);

        left = service.getSupportLeftOfPos(1.5f);
        assertEquals(support, left);

    }

    @Test
    public void testGetSupportRightOfPos() {
        Support support1 = new Support(storage, 0.2f, 1f, 3f, 0);

        Support right = service.getSupportRightOfPos(1.5f);
        assertEquals(support1, right);

        Support support2 = new Support(storage, 0.2f, 1f, 4f, 0);

        right = service.getSupportRightOfPos(1.5f);
        assertEquals(support1, right);

    }

    @Test
    public void testGetBoardsAt() {

        Support support1 = new Support(storage, 0.2f, 1f, 3f, 0);
        Support support2 = new Support(storage, 0.2f, 1f, 4f, 0);

        List<Board> boards = new ArrayList<>();
        Board board1 = new Board(support1, support2, 02f, 0);
        Board board2 = new Board(support1, support2, 02f, 0.2f);
        Board board3 = new Board(support1, support2, 02f, 0.4f);

        boards.add(board1);
        boards.add(board2);
        boards.add(board3);

        List<Board> b = service.getBoardsAt(3.5f);

        assertEquals(boards, b);

    }

    @Test
    public void testGetBoardBelow() {

        Support support1 = new Support(storage, 0.2f, 1f, 3f, 0);
        Support support2 = new Support(storage, 0.2f, 1f, 4f, 0);

        List<Board> boards = new ArrayList<>();
        Board board1 = new Board(support1, support2, 02f, 0);
        Board board2 = new Board(support1, support2, 02f, 0.2f);
        Board board3 = new Board(support1, support2, 02f, 0.4f);

        boards.add(board1);
        boards.add(board2);
        boards.add(board3);

        List<Board> b = service.getBoardsAt(3.5f);

        assertEquals(board3, service.getBoardBelow(boards, 0.7f));
        assertEquals(board2, service.getBoardBelow(boards, 0.8f));
        assertEquals(board1, service.getBoardBelow(boards, 1f));

    }

}
