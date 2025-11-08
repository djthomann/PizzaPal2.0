package pizzapal.ui.view.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.view.entities.support.SupportView;
import pizzapal.ui.view.entities.support.SupportViewController;
import pizzapal.utils.Helper;
import pizzapal.utils.ToolState;

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

        ToolState toolState = new ToolState();

        this.controller = new SupportViewController(controller, toolState, support);

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
        assertEquals(Helper.convertMetersToPixel(0.2f), view.getWidth(), DELTA);
        assertEquals(Helper.convertMetersToPixel(2f), view.getHeight(), DELTA);
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

    }

    @Test
    public void testContextMenuOpensAndCloses() {
        assertTrue(!controller.getContextMenu().isShowing());

        rightClickOn(view);
        assertTrue(controller.getContextMenu().isShowing());

        rightClickOn(view);
        assertTrue(!controller.getContextMenu().isShowing());
    }

    @Test
    public void testPositionAfterMoving() {

        Point2D topLeftOfView = view.localToScreen(0, 0);
        Point2D screenPoint = container.localToScreen(0, 0);

        moveTo(topLeftOfView).press(MouseButton.PRIMARY);
        moveTo(screenPoint).release(MouseButton.PRIMARY);

        assertEquals(0, view.getLayoutX(), DELTA);
        assertEquals(150, view.getLayoutY(), DELTA);

        screenPoint = container.localToScreen(150, 100);
        topLeftOfView = view.localToScreen(0, 0);

        moveTo(topLeftOfView).press(MouseButton.PRIMARY);
        moveTo(screenPoint).release(MouseButton.PRIMARY);

        assertEquals(150, view.getLayoutX(), DELTA);
        assertEquals(150, view.getLayoutY(), DELTA);
    }

    @Test
    public void testDeleteByContextMenu() {

        assertEquals(support, storage.getSupports().get(0));

        rightClickOn(view);
        clickOn("Delete");

        assertTrue(storage.isEmpty());

    }

    @Test
    public void testEditWidthViaContextMenu() {

        float startingTestWidth = 0.5f;
        float endingTestWidth = 2.2f;
        float testWidthIncrement = 0.5f;

        for (float width = startingTestWidth; width < endingTestWidth; width += testWidthIncrement) {
            rightClickOn(view);
            TextField textField = lookup("#Width").queryAs(TextField.class);
            clickOn(textField);
            eraseText(textField.getText().length());
            write(String.valueOf(width));

            clickOn("Edit");

            assertEquals(Helper.convertMetersToPixel(width), view.getWidth(), DELTA);
        }

    }

    @Test
    public void testEditHeightViaContextMenu() {

        float startingTestHeight = 0.5f;
        float endingTestHeight = 2.2f;
        float testHeightIncrement = 0.5f;

        for (float height = startingTestHeight; height < endingTestHeight; height += testHeightIncrement) {
            rightClickOn(view);
            TextField textField = lookup("#Height").queryAs(TextField.class);
            clickOn(textField);
            eraseText(textField.getText().length());
            write(String.valueOf(height));

            clickOn("Edit");

            assertEquals(Helper.convertMetersToPixel(height), view.getHeight(), DELTA);
        }
    }

    @Test
    public void testEditDimensionsViaContextMenu() {
        rightClickOn(view);

        TextField textFieldWidth = lookup("#Width").queryAs(TextField.class);
        clickOn(textFieldWidth);
        eraseText(textFieldWidth.getText().length());
        write("0.5");

        TextField textFieldHeight = lookup("#Height").queryAs(TextField.class);
        clickOn(textFieldHeight);
        eraseText(textFieldHeight.getText().length());
        write("2.5");

        clickOn("Edit");

        assertEquals(0.5f, support.getWidth(), DELTA);
        assertEquals(2.5f, support.getHeight(), DELTA);
    }

}
