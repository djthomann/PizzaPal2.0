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

public class StorageViewTest extends ApplicationTest {

    private StorageView view;

    @Override
    public void start(Stage stage) throws Exception {
        Storage storage = new Storage(6f, 3f);

        StorageController controller = new StorageController(storage);

        view = new StorageViewController(controller).getView();

        Scene scene = new Scene(view);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testInitialSize() {
        assertEquals(900, view.getWidth());
        assertEquals(450, view.getHeight());
    }

}
