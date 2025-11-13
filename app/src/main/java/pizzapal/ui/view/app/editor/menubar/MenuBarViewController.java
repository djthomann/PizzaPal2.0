package pizzapal.ui.view.app.editor.menubar;

import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import pizzapal.ui.view.app.editor.EditorViewController;
import pizzapal.utils.SceneManager;

public class MenuBarViewController {

    private MenuBarView view;

    private EditorViewController editorViewController;

    public MenuBarViewController(EditorViewController editorViewController) {
        view = new MenuBarView();
        this.editorViewController = editorViewController;

        init();
    }

    public void init() {

        MenuItem newItem = view.getNewItem();
        newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        newItem.setOnAction(e -> {
            editorViewController.addStorageTab();
        });

        MenuItem openItem = view.getOpenItem();
        openItem.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        openItem.setOnAction(e -> {
            editorViewController.openStorage();
        });

        MenuItem saveItem = view.getSaveItem();
        saveItem.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));
        saveItem.setOnAction(e -> {
            editorViewController.saveStorage();
        });

        MenuItem closeItem = view.getCloseItem();
        closeItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        closeItem.setOnAction(e -> {
            SceneManager.getInstance().showMainMenu(); // TODO: Close current Tab?
        });

        MenuItem undoItem = view.getUndoItem();
        undoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        undoItem.setOnAction(e -> {
            editorViewController.undo();
        });

        MenuItem redoItem = view.getRedoItem();
        redoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));
        redoItem.setOnAction(e -> {
            editorViewController.redo();
        });

        MenuItem toggleToolBarItem = view.getToogleToolBarItem();
        toggleToolBarItem.setOnAction(e -> {
            editorViewController.toggleToolBar();
        });

    }

    public MenuBarView getView() {
        return view;
    }

}
