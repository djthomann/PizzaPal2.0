package pizzapal.ui.view.app.editor;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

public class MenuBarView extends MenuBar {

    private MenuItem newItem;
    private MenuItem openItem;
    private MenuItem saveItem;
    private MenuItem closeItem;

    private MenuItem undoItem;
    private MenuItem redoItem;

    private MenuItem toggleToolbarItem;

    public MenuBarView() {

        // FILE MENU
        Menu fileMenu = new Menu("File");
        newItem = new MenuItem("New");
        openItem = new MenuItem("Open");
        saveItem = new MenuItem("Save");
        closeItem = new MenuItem("Close");
        fileMenu.getItems().addAll(newItem, openItem, saveItem, new SeparatorMenuItem(), closeItem);

        // EDIT MENU
        Menu editMenu = new Menu("Edit");
        undoItem = new MenuItem("Undo");
        redoItem = new MenuItem("Redo");
        editMenu.getItems().addAll(undoItem, redoItem);

        // VIEW MENU
        Menu viewMenu = new Menu("View");
        toggleToolbarItem = new MenuItem("Show/Hide Toolbar");
        viewMenu.getItems().add(toggleToolbarItem);

        // HELP MENU
        Menu helpMenu = new Menu("Help");
        MenuItem shortcutItem = new MenuItem("Shortcuts");
        MenuItem aboutItem = new MenuItem("About");
        helpMenu.getItems().addAll(shortcutItem, aboutItem);

        this.getMenus().addAll(fileMenu, editMenu, viewMenu, helpMenu);

    }

    public MenuItem getNewItem() {
        return newItem;
    }

    public MenuItem getOpenItem() {
        return openItem;
    }

    public MenuItem getSaveItem() {
        return saveItem;
    }

    public MenuItem getCloseItem() {
        return closeItem;
    }

    public MenuItem getUndoItem() {
        return undoItem;
    }

    public MenuItem getRedoItem() {
        return redoItem;
    }

    public MenuItem getToogleToolBarItem() {
        return toggleToolbarItem;
    }

}
