package pizzapal.ui.view.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.view.entities.support.SupportView;
import pizzapal.ui.view.entities.support.SupportViewController;
import pizzapal.utils.Helper;

public class SupportViewTest extends ApplicationTest {

    private Support support;

    private SupportView view;

    private SupportViewController controller;

    private Storage storage;

    private Pane container;

    private static final double DELTA = 0.001;

    @Override
    public void start(Stage stage) throws Exception {
        storage = new Storage(6f, 3f);

        StorageController controller = new StorageController(storage);

        support = new Support(storage, 0.2f, 2f, 3f, 0);

        this.controller = new SupportViewController(controller, support);

        view = (SupportView) this.controller.getView();

        container = new Pane();
        container.setPrefSize(Helper.convertMetersToPixel(6f), Helper.convertMetersToPixel(3f));
        container.getChildren().addAll(view);

        Scene scene = new Scene(container);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testSizeAfterCreate() {
        assertEquals(30, view.getWidth(), DELTA);
        assertEquals(300, view.getHeight(), DELTA);
    }

    @Test
    public void testPositionAfterCreate() {
        assertEquals(450, view.getLayoutX(), DELTA);
        assertEquals(150, view.getLayoutY(), DELTA);
    }

    @Test
    public void testContextMenuOpens() {
        assertTrue(!controller.getContextMenu().isShowing());

        clickOn(view, MouseButton.SECONDARY);
        assertTrue(controller.getContextMenu().isShowing());

        clickOn(view, MouseButton.SECONDARY);
        assertTrue(!controller.getContextMenu().isShowing());
    }

    @Test
    public void testPositionAfterMoving() {
        Point2D screenPoint = container.localToScreen(0, 0);

        drag(view).dropTo(screenPoint);

        assertEquals(0, view.getLayoutX(), DELTA);
        assertEquals(150, view.getLayoutY(), DELTA);

        screenPoint = container.localToScreen(150, 100);

        drag(view).dropTo(screenPoint);

        assertEquals(150, view.getLayoutX(), DELTA);
        assertEquals(150, view.getLayoutY(), DELTA);
    }

    @Test
    public void testDeleteByContextMenu() {

        assertEquals(support, storage.getSupports().get(0));

        clickOn(view, MouseButton.SECONDARY);
        clickOn("Delete");

        assertTrue(storage.isEmpty());

    }

}
