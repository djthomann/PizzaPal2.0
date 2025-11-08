package pizzapal.ui.view.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.scene.Scene;
import javafx.stage.Stage;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.ui.view.entities.storage.StorageView;
import pizzapal.ui.view.entities.storage.StorageViewController;
import pizzapal.utils.Helper;

public class StorageViewTest extends ApplicationTest {

    private StorageView view;

    private final float width = 6f;
    private final float height = 3f;

    @Override
    public void start(Stage stage) throws Exception {
        Storage storage = new Storage(width, height);

        StorageController controller = new StorageController(storage);

        view = new StorageViewController(controller).getView();

        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testInitialSize() {
        assertEquals(Helper.convertMetersToPixel(width), view.getWidth());
        assertEquals(Helper.convertMetersToPixel(height), view.getHeight());
    }

}
