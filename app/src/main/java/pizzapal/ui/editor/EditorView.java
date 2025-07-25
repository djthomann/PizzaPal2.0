package pizzapal.ui.editor;

import javafx.geometry.Orientation;
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
import pizzapal.SceneManager;
import pizzapal.model.repository.StorageRepository;
import pizzapal.model.storage.Storage;
import pizzapal.model.storage.StorageController;
import pizzapal.ui.storage.StorageView;

public class EditorView extends BorderPane {

    private TabPane tabPane;
    private MenuBar menuBar;
    private ToolBar toolBar;

    public EditorView(Storage storage) {

        StorageController storageController = new StorageController(storage);

        tabPane = new TabPane();
        Tab tab1 = new Tab("Lager A", new StorageView(storageController));
        tabPane.getTabs().add(tab1);

        menuBar = new MenuBar();
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
        menuBar.getMenus().addAll(fileMenu);

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
        menuBar.getMenus().addAll(editMenu);

        toolBar = new ToolBar();
        Button selectButton = new Button("Auswählen");
        Button drawButton = new Button("Zeichnen");
        Button eraseButton = new Button("Löschen");

        toolBar.setOrientation(Orientation.VERTICAL);
        toolBar.getItems().addAll(selectButton, drawButton, eraseButton);

        setCenter(tabPane);
        setLeft(toolBar);
        setTop(menuBar);

    }

    public void addStorageTab() {
        Storage storage = StorageRepository.getInstance().createStorage();
        Tab tab = new Tab("Lager 2", new StorageView(new StorageController(storage)));
        tabPane.getTabs().add(tab);
        tabPane.getSelectionModel().select(tab);
    }

}
