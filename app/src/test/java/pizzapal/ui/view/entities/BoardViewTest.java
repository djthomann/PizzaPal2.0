package pizzapal.ui.view.entities;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.view.entities.board.BoardView;
import pizzapal.ui.view.entities.board.BoardViewController;
import pizzapal.ui.view.entities.support.SupportViewController;
import pizzapal.utils.Helper;
import pizzapal.utils.ToolState;

public class BoardViewTest extends ApplicationTest {

    private Board board;

    private BoardView boardView;

    private BoardViewController boardViewController;

    private Storage storage;

    private StorageController storageController;

    private Support leftSupport;

    private Support rightSupport;

    private Pane container;

    private static final double DELTA = 0.001;

    @Override
    public void start(Stage stage) {
        storage = new Storage(6f, 3f);

        StorageController controller = new StorageController(storage);

        leftSupport = new Support(storage, 0.2f, 2f, 2f, 0);
        rightSupport = new Support(storage, 0.2f, 2f, 4f, 0);

        board = new Board(rightSupport, leftSupport, 0.2f, 0);

        ToolState toolstate = new ToolState();

        container = new Pane();
        container.setPrefSize(Helper.convertMetersToPixel(6f), Helper.convertMetersToPixel(3f));
        container.getChildren().addAll(
                new BoardViewController(controller, toolstate, board).getView(),
                new SupportViewController(controller, toolstate, leftSupport).getView(),
                new SupportViewController(controller, toolstate, rightSupport).getView());

        Scene scene = new Scene(container);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testSizeAfterCreate() {
        // assertEquals(Helper.convertMetersToPixel(4f - 2f - 0.2f), board.getWidth(),
        // DELTA);

    }

    @Test
    public void testPositionAfterCreate() {

    }

}
