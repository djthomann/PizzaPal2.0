package pizzapal.ui.view.entities;

import static org.junit.Assert.assertEquals;
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
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.view.entities.board.BoardViewController;
import pizzapal.ui.view.entities.item.ItemView;
import pizzapal.ui.view.entities.item.ItemViewController;
import pizzapal.ui.view.entities.support.SupportViewController;
import pizzapal.utils.Config;
import pizzapal.utils.Helper;
import pizzapal.utils.ToolState;

public class ItemViewTest extends ApplicationTest {

    private Item item;
    private ItemView view;
    private ItemViewController controller;

    private Storage storage;
    private StorageController storageController;

    private Support leftSupport, rightSupport;
    private Board topBoard, bottomBoard;

    private Pane container;

    private static final double DELTA = 0.001;

    @Override
    public void start(Stage stage) {
        storage = new Storage(6f, 3f);

        storageController = new StorageController(storage);

        leftSupport = new Support(storage, 0.2f, 2f, 2f, 0);
        rightSupport = new Support(storage, 0.2f, 2f, 4f, 0);

        topBoard = new Board(leftSupport, rightSupport, 0.2f, 0.8f);
        bottomBoard = new Board(leftSupport, rightSupport, 0.2f, 1.6f);

        item = new Item(topBoard, 0.5f, 0.3f, 0.5f, 1f);

        ToolState toolstate = new ToolState();

        container = new Pane();
        container.setPrefSize(Helper.convertMetersToPixel(6f), Helper.convertMetersToPixel(3f));

        controller = new ItemViewController(storageController, toolstate, item);
        view = (ItemView) controller.getView();

        container.getChildren().addAll(
                view,
                new BoardViewController(storageController, toolstate, topBoard).getView(),
                new BoardViewController(storageController, toolstate, bottomBoard).getView(),
                new SupportViewController(storageController, toolstate, leftSupport).getView(),
                new SupportViewController(storageController, toolstate, rightSupport).getView());

        Scene scene = new Scene(container);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testSizeAfterCreate() {
        assertEquals(item.getWidth() * Config.PIXEL_PER_METER, view.getWidth(), DELTA);
        assertEquals(item.getHeight() * Config.PIXEL_PER_METER, view.getHeight(), DELTA);
    }

    @Test
    public void testPositionAfterCreate() {
        assertEquals(item.getPosX() * Config.PIXEL_PER_METER, view.getLayoutX(), DELTA);
        assertEquals(item.getPosY() * Config.PIXEL_PER_METER, view.getLayoutY(), DELTA);
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
    public void testPositionAfterMovingOnSameBoard() {

        Platform.runLater(() -> {
            view.toFront();
        });

        float posXbefore = item.getPosX();
        float deltaX = 0.5f;

        float posYbefore = item.getPosY();
        float deltaY = -0.25f;

        Point2D topLeftOfView = view.localToScreen(0, 0);
        Point2D screenPoint = topLeftOfView.add(deltaX * Config.PIXEL_PER_METER, deltaY * Config.PIXEL_PER_METER);

        moveTo(topLeftOfView).press(MouseButton.PRIMARY);
        moveTo(screenPoint).release(MouseButton.PRIMARY);

        assertEquals((posXbefore + deltaX) * Config.PIXEL_PER_METER, view.getLayoutX(), DELTA);
        assertEquals((posYbefore) * Config.PIXEL_PER_METER, view.getLayoutY(), DELTA);
    }

    @Test
    public void testPositionAfterMovingBetweenBoards() {
        Platform.runLater(() -> {
            view.toFront();
        });

        float posXbefore = item.getPosX();
        float deltaX = 0;
        float deltaY = 0.6f;

        Point2D topLeftOfView = view.localToScreen(0, 0);
        Point2D screenPoint = topLeftOfView.add(deltaX * Config.PIXEL_PER_METER, deltaY * Config.PIXEL_PER_METER);

        moveTo(topLeftOfView).press(MouseButton.PRIMARY);
        moveTo(screenPoint).release(MouseButton.PRIMARY);

        assertEquals(posXbefore * Config.PIXEL_PER_METER, view.getLayoutX(), DELTA);
        assertEquals(bottomBoard.getPosY() + item.getHeight() * Config.PIXEL_PER_METER, view.getLayoutY(), DELTA);

    }

}
