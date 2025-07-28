package pizzapal.ui.view.app.editor;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCombination;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.repository.StorageRepository;
import pizzapal.ui.view.entities.storage.StorageView;
import pizzapal.ui.view.entities.storage.StorageViewController;
import pizzapal.utils.SceneManager;
import pizzapal.utils.ToolState;

public class EditorViewController {

    private EditorView view;

    private Map<Tab, StorageController> controllerMap;

    private ToolState toolState;

    public EditorViewController(Storage storage) {

        view = new EditorView();
        controllerMap = new HashMap<>();

        StorageController storageController = new StorageController(storage);
        toolState = new ToolState();

        StorageView storageView = new StorageViewController(storageController, toolState).getView();
        Tab tab1 = new Tab("Storage 1", storageView);

        ToolBarView toolBarView = new ToolBarViewController(toolState).getView();

        addStorageTab(tab1, storageController);

        initMenuBar(view.getMenuBar());

        view.addToolBar(toolBarView);

    }

    public void initMenuBar(MenuBarView menuBar) {
        MenuItem newItem = menuBar.getNewItem();
        newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        newItem.setOnAction(_ -> {
            addStorageTab();
        });

        MenuItem closeItem = menuBar.getCloseItem();
        closeItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        closeItem.setOnAction(_ -> {
            SceneManager.getInstance().showMainMenu();
        });

        MenuItem undoItem = menuBar.getUndoItem();
        undoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        undoItem.setOnAction(_ -> {
            controllerMap.get(view.getTabPane().getSelectionModel().getSelectedItem()).undo();
        });

        MenuItem redoItem = menuBar.getRedoItem();
        redoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));
        redoItem.setOnAction(_ -> {
            controllerMap.get(view.getTabPane().getSelectionModel().getSelectedItem()).redo();
        });

        MenuItem toggleToolBarItem = menuBar.getToogleToolBarItem();
        toggleToolBarItem.setOnAction(_ -> {

            if (view.isToolBarVisible()) {
                view.hideToolBar();
            } else {
                view.showToolBar();
            }
        });
    }

    public void addStorageTab(Tab tab, StorageController controller) {
        controllerMap.put(tab, controller);
        tab.setOnCloseRequest(_ -> {
            controllerMap.remove(tab);
        });
        view.getTabPane().getTabs().add(tab);
        view.getTabPane().getSelectionModel().select(tab);
    }

    public void addStorageTab() {
        Storage storage = StorageRepository.getInstance().createStorage();
        StorageController newStorageController = new StorageController(storage);
        Tab tab = new Tab("New Storage", new StorageViewController(newStorageController, toolState).getView());
        addStorageTab(tab, newStorageController);
    }

    public EditorView getView() {
        return view;
    }

}
