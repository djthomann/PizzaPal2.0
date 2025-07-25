package pizzapal.ui.editor;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import pizzapal.NotificationManager;
import pizzapal.SceneManager;
import pizzapal.model.repository.StorageRepository;
import pizzapal.model.storage.Storage;
import pizzapal.model.storage.StorageController;
import pizzapal.ui.components.NotificationDropdown;
import pizzapal.ui.storage.StorageView;

public class EditorView extends BorderPane {

    private StorageController storageController;

    private TabPane tabPane;
    private MenuBar menuBar;
    private HBox menuRow;
    private ToolBar toolBar;

    public EditorView(Storage storage) {

        storageController = new StorageController(storage);

        tabPane = new TabPane();
        Tab tab1 = new Tab("Lager A", new StorageView(storageController));
        tabPane.getTabs().add(tab1);

        initMenuBar();

        initMenuRow();

        toolBar = new ToolBar();
        Button selectButton = new Button("Auswählen");
        Button drawButton = new Button("Zeichnen");
        Button eraseButton = new Button("Löschen");
        Button supportButton = new Button("Support");

        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.getItems().addAll(selectButton, drawButton, eraseButton, supportButton);

        setCenter(tabPane);
        setLeft(toolBar);
        setTop(menuRow);

    }

    public void initMenuRow() {
        MenuBar notificationCenter = new MenuBar();
        notificationCenter.setBackground(null);
        Menu notificationMenu = new Menu("Notifications");
        MenuItem notificationItem = new MenuItem("Notfications");
        notificationMenu.getItems().add(notificationItem);
        notificationCenter.getMenus().add(notificationMenu);

        NotificationManager.getInstance().addListener(newNotifications -> {
            Platform.runLater(() -> {
                notificationMenu.getItems().clear();
                for (String n : newNotifications) {
                    notificationMenu.getItems().add(new MenuItem(n));
                }
            });
        });

        NotificationDropdown dropdown = new NotificationDropdown();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        menuRow = new HBox(menuBar, spacer, dropdown.getButton());
    }

    public void initMenuBar() {
        menuBar = new MenuBar();
        menuBar.setBackground(null);

        // FILE MENU
        Menu fileMenu = new Menu("File");
        MenuItem newItem = new MenuItem("New");
        newItem.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));
        newItem.setOnAction(e -> {
            addStorageTab();
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
            storageController.undo();
        });
        MenuItem redoItem = new MenuItem("Redo");
        redoItem.setAccelerator(KeyCombination.keyCombination("Ctrl+Y"));
        redoItem.setOnAction(e -> {
            storageController.redo();
        });
        editMenu.getItems().addAll(undoItem, redoItem);

        // VIEW MENU
        Menu viewMenu = new Menu("View");
        MenuItem toggleToolbarItem = new MenuItem("Show/Hide Toolbar");
        toggleToolbarItem.setOnAction(e -> {
            Node node = this.getLeft();
            if (node == null) {
                this.setLeft(toolBar);
            } else {
                this.setLeft(null);
            }
        });
        viewMenu.getItems().add(toggleToolbarItem);

        // HELP MENU
        Menu helpMenu = new Menu("Help");
        MenuItem shortcutItem = new MenuItem("Shortcuts");
        MenuItem aboutItem = new MenuItem("About");
        helpMenu.getItems().addAll(shortcutItem, aboutItem);

        menuBar.getMenus().addAll(fileMenu, editMenu, viewMenu, helpMenu);
    }

    public void addStorageTab() {
        Storage storage = StorageRepository.getInstance().createStorage();
        Tab tab = new Tab("Lager 2", new StorageView(new StorageController(storage)));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

}
