package pizzapal.ui.view.app.editor;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.ui.view.entities.storage.StorageView;
import pizzapal.ui.view.entities.storage.StorageViewController;

public class EditorView extends BorderPane {

    private StorageController storageController;

    private Map<Tab, StorageController> controllerMap;

    public EditorView(Storage storage) {

        storageController = new StorageController(storage);

        controllerMap = new HashMap<>();

        TabPane tabPane = new TabPane();
        StorageView storageView = new StorageViewController(storageController).getView();
        Tab tab1 = new Tab("New Storage", storageView);
        controllerMap.put(tab1, storageController);
        tabPane.getTabs().add(tab1);
        tab1.setOnCloseRequest(_ -> {
            controllerMap.remove(tab1);
        });

        MenuBarView menuBar = new MenuBarView(controllerMap, tabPane);
        ToolBarView toolBar = new ToolBarView();

        setCenter(tabPane);
        setLeft(toolBar);
        setTop(menuBar);

    }

    /*
     * public void addStorageTab() {
     * Storage storage = StorageRepository.getInstance().createStorage();
     * StorageController newStorageController = new StorageController(storage);
     * Tab tab = new Tab("New Storage", new
     * StorageViewController(newStorageController).getView());
     * tabPane.getTabs().add(tab);
     * tabPane.getSelectionModel().select(tab);
     * controllerMap.put(tab, newStorageController);
     * tab.setOnCloseRequest(_ -> {
     * controllerMap.remove(tab);
     * });
     * }
     */

}
