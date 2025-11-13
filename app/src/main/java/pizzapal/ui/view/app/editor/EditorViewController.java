package pizzapal.ui.view.app.editor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.control.Tab;
import javafx.scene.input.KeyEvent;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.repository.StorageRepository;
import pizzapal.ui.view.app.editor.menubar.MenuBarView;
import pizzapal.ui.view.app.editor.menubar.MenuBarViewController;
import pizzapal.ui.view.app.editor.toolbar.ToolBarView;
import pizzapal.ui.view.app.editor.toolbar.ToolBarViewController;
import pizzapal.ui.view.entities.storage.StorageView;
import pizzapal.ui.view.entities.storage.StorageViewController;
import pizzapal.utils.ToolState;
import pizzapal.utils.ToolState.Tool;

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
        Tab tab1 = new Tab(storage.getName(), storageView);
        addStorageTab(tab1, storageController);

        MenuBarView menuBarView = new MenuBarViewController(this).getView();
        view.addMenuBar(menuBarView);

        ToolBarView toolBarView = new ToolBarViewController(toolState).getView();
        view.setToolBar(toolBarView);

        view.setOnKeyPressed(event -> handleKeyPressed(event));

    }

    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case V -> {
                toolState.setCurrentTool(Tool.SELECT);
            }
            case S -> {
                toolState.setCurrentTool(Tool.SUPPORT);
            }
            case B -> {
                toolState.setCurrentTool(Tool.BOARD);
            }
            case I -> {
                toolState.setCurrentTool(Tool.ITEM);
            }
            default -> {
                break;
            }
        }
    }

    public void addStorageTab() {
        addStorageTab(StorageRepository.createStorage(), "New Storage.storage");
    }

    public void addStorageTab(Storage storage, String text) {
        StorageController newStorageController = new StorageController(storage);
        Tab tab = new Tab(text, new StorageViewController(newStorageController, toolState).getView());
        addStorageTab(tab, newStorageController);
    }

    public void addStorageTab(Tab tab, StorageController controller) {
        controllerMap.put(tab, controller);
        tab.setOnCloseRequest(e -> {
            controllerMap.remove(tab);
        });
        view.getTabPane().getTabs().add(tab);
        view.getTabPane().getSelectionModel().select(tab);
    }

    public void saveStorage() {
        try {
            Tab currentTab = view.getSelectedTab();
            Storage currentStorage = controllerMap.get(currentTab).getStorage();
            StorageRepository.saveToFile(currentStorage);
        } catch (IOException event) {
            event.printStackTrace();
        }
    }

    public void openStorage() {
        Storage storage = StorageRepository.loadFromFileChooser();

        if (storage != null) {
            storage.initListeners();
            addStorageTab(storage, storage.getName());
        }
    }

    public void toggleToolBar() {
        if (view.isToolBarVisible()) {
            view.hideToolBar();
        } else {
            view.showToolBar();
        }
    }

    public void undo() {
        controllerMap.get(view.getTabPane().getSelectionModel().getSelectedItem()).undo();
    }

    public void redo() {
        controllerMap.get(view.getTabPane().getSelectionModel().getSelectedItem()).redo();
    }

    public EditorView getView() {
        return view;
    }

}
