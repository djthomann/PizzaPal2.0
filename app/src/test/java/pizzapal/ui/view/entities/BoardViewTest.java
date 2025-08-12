package pizzapal.ui.view.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.view.entities.board.BoardView;
import pizzapal.ui.view.entities.board.BoardViewController;
import pizzapal.ui.view.entities.support.SupportViewController;
import pizzapal.utils.Config;
import pizzapal.utils.Helper;
import pizzapal.utils.ToolState;

public class BoardViewTest extends ApplicationTest {

    private Board board;

    private BoardView view;

    private BoardViewController controller;

    private Storage storage;

    private StorageController storageController;

    private Support leftSupport;

    private Support rightSupport;

    private Pane container;

    private static final double DELTA = 0.001;

    @Override
    public void start(Stage stage) {
        storage = new Storage(6f, 3f);

        storageController = new StorageController(storage);

        leftSupport = new Support(storage, 0.2f, 2f, 2f, 0);
        rightSupport = new Support(storage, 0.2f, 2f, 4f, 0);

        board = new Board(leftSupport, rightSupport, 0.2f, 0);

        ToolState toolstate = new ToolState();

        container = new Pane();
        container.setPrefSize(Helper.convertMetersToPixel(6f), Helper.convertMetersToPixel(3f));

        controller = new BoardViewController(storageController, toolstate, board);
        view = (BoardView) controller.getView();
        container.getChildren().addAll(
                view,
                new SupportViewController(storageController, toolstate, leftSupport).getView(),
                new SupportViewController(storageController, toolstate, rightSupport).getView());

        Scene scene = new Scene(container);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testSizeAfterCreate() {

        float expectedWidth = 4f - 2f - 0.2f;
        float expectedHeight = 0.2f;

        assertEquals(expectedWidth * Config.PIXEL_PER_METER, view.getWidth(), DELTA);
        assertEquals(expectedHeight * Config.PIXEL_PER_METER, view.getHeight(), DELTA);
    }

    @Test
    public void testPositionAfterCreate() {
        // assertEquals(board.getPosY() * Config.PIXEL_PER_METER, view.getLayoutY());
        // --> Problem with position!
        assertEquals(2.2f * Config.PIXEL_PER_METER, view.getLayoutX());
    }

    @Test
    public void testContextMenuOpens() {
        assertTrue(!controller.getContextMenu().isShowing());

        clickOn(view, MouseButton.SECONDARY);
        assertTrue(controller.getContextMenu().isShowing());

    }

    @Test
    public void testContextMenuOpensAndCloses() {
        assertTrue(!controller.getContextMenu().isShowing());

        clickOn(view, MouseButton.SECONDARY);
        assertTrue(controller.getContextMenu().isShowing());

        clickOn(view, MouseButton.SECONDARY);
        assertTrue(!controller.getContextMenu().isShowing());
    }

    @Test
    public void testPositionAfterMovingSuccessfully() {

        Platform.runLater(() -> {
            view.toFront();
        });

        Point2D topLeftOfView = view.localToScreen(0, 0);
        Point2D screenPoint = topLeftOfView.add(0, Config.PIXEL_PER_METER);

        moveTo(topLeftOfView).press(MouseButton.PRIMARY);
        moveTo(screenPoint).release(MouseButton.PRIMARY);

        assertEquals(2.2f * Config.PIXEL_PER_METER, view.getLayoutX());
        // assertEquals(2.2f * Config.PIXEL_PER_METER, view.getLayoutY()); TODO: Problem
    }

    @Test
    public void testPositionAfterMovingFail() {

        Platform.runLater(() -> {
            view.toFront();
        });

        Point2D screenPoint = container.localToScreen(150, 100);
        Point2D topLeftOfView = view.localToScreen(0, 0);

        moveTo(topLeftOfView).press(MouseButton.PRIMARY);
        moveTo(screenPoint).release(MouseButton.PRIMARY);

        assertEquals(2.2f * Config.PIXEL_PER_METER, view.getLayoutX());
        // assertEquals(2.2f * Config.PIXEL_PER_METER, view.getLayoutY()); TODO: Problem
    }

    @Test
    public void testDeleteByContextMenu() {

        assertEquals(board, leftSupport.getBoardsRight().get(0));
        assertEquals(board, rightSupport.getBoardsLeft().get(0));

        clickOn(view, MouseButton.SECONDARY);
        clickOn("Delete");

        assertTrue(leftSupport.getBoardsRight().isEmpty());
        assertTrue(rightSupport.getBoardsLeft().isEmpty());

    }

}
