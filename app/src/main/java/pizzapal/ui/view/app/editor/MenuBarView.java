package pizzapal.ui.view.app.editor;

import java.util.Map;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import pizzapal.model.controller.StorageController;
import pizzapal.ui.components.NotificationDropdown;
import pizzapal.utils.NotificationManager;

public class MenuBarView extends HBox {

    private MenuItem newItem;
    private MenuItem closeItem;

    private MenuItem undoItem;
    private MenuItem redoItem;

    private MenuItem toggleToolbarItem;

    public MenuBarView(Map<Tab, StorageController> controllerMap, TabPane tabPane) {

        MenuBar menuBar = new MenuBar();
        menuBar.setBackground(null);

        // FILE MENU
        Menu fileMenu = new Menu("File");
        newItem = new MenuItem("New");
        MenuItem openItem = new MenuItem("Open");
        MenuItem saveItem = new MenuItem("Save");
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

        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, helpMenu);

        MenuBar notificationCenter = new MenuBar();
        notificationCenter.setBackground(null);
        Menu notificationMenu = new Menu("Notifications");
        MenuItem notificationItem = new MenuItem("Notfications");
        notificationMenu.getItems().add(notificationItem);
        notificationCenter.getMenus().add(notificationMenu);

        NotificationDropdown dropdown = new NotificationDropdown();

        NotificationManager.getInstance().addListener(newNotifications -> {
            Platform.runLater(() -> {
                if (newNotifications.isEmpty()) {
                    dropdown.hide();
                } else {
                    dropdown.show();
                }
                notificationMenu.getItems().clear();
                for (String n : newNotifications) {
                    notificationMenu.getItems().add(new MenuItem(n));
                }
            });
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.getChildren().addAll(menuBar, spacer, dropdown.getButton());

    }

    public MenuItem getNewItem() {
        return newItem;
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
