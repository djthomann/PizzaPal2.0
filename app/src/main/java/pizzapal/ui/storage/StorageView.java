package pizzapal.ui.storage;

import java.util.List;

import javafx.scene.layout.Pane;
import pizzapal.Helper;
import pizzapal.model.Storage;
import pizzapal.model.Support;
import pizzapal.ui.UIConfig;
import pizzapal.ui.support.SupportController;

public class StorageView extends Pane {

    public StorageView(Storage storage) {

        this.setPrefSize(Helper.convertMetersToPixel(storage.getWidth()),
                Helper.convertMetersToPixel(storage.getHeight()));

        this.setBackground(UIConfig.STORAGE_BACKGROUND);

        List<Support> supports = storage.getSupports();

        for (Support support : supports) {
            this.getChildren().add(new SupportController(support).getView());
        }

    }

}
