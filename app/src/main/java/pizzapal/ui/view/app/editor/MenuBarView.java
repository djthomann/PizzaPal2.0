package pizzapal.ui.view.app.editor;

import java.util.Map;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import pizzapal.model.controller.StorageController;
import pizzapal.ui.components.NotificationDropdown;
import pizzapal.utils.NotificationManager;
import pizzapal.utils.SceneManager;

// TODO: EditorViewController !!!

public class MenuBarView extends HBox {

    public MenuBarView(Map<Tab, StorageController> controllerMap, TabPane tabPane) {

        MenuBar menuBar = new MenuBar();
        menuBar.setBackground(null);

        // FILE MENU
        Menu fileMenu = new Menu("File");
        MenuItem newItem = new MenuItem("New");
        newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        newItem.setOnAction(e -> {
            // addStorageTab();
        });
        MenuItem openItem = new MenuItem("Open");
        MenuItem exitItem = new MenuItem("Close");
        exitItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));
        exitItem.setOnAction(e -> {
            SceneManager.getInstance().showMainMenu();
        });
        fileMenu.getItems().addAll(newItem, openItem, new SeparatorMenuItem(), exitItem);

        // EDIT MENU
        Menu editMenu = new Menu("Edit");
        MenuItem undoItem = new MenuItem("Undo");
        undoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));
        undoItem.setOnAction(e -> {
            controllerMap.get(tabPane.getSelectionModel().getSelectedItem()).undo();
        });
        MenuItem redoItem = new MenuItem("Redo");
        redoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));
        redoItem.setOnAction(e -> {
            controllerMap.get(tabPane.getSelectionModel().getSelectedItem()).redo();
        });
        editMenu.getItems().addAll(undoItem, redoItem);

        // VIEW MENU
        Menu viewMenu = new Menu("View");
        MenuItem toggleToolbarItem = new MenuItem("Show/Hide Toolbar");
        toggleToolbarItem.setOnAction(e -> {
            /*
             * Node node = this.getLeft();
             * if (node == null) {
             * // this.setLeft(toolBar);
             * } else {
             * // this.setLeft(null);
             * }
             */
        });
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

}
