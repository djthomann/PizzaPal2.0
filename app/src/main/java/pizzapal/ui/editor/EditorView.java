package pizzapal.ui.editor;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import pizzapal.model.storage.Storage;
import pizzapal.model.storage.StorageController;
import pizzapal.ui.storage.StorageView;

public class EditorView extends BorderPane {

    public EditorView(Storage storage) {

        StorageController storageController = new StorageController(storage);

        StorageView storageView = new StorageView(storageController);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Datei");
        MenuItem newItem = new MenuItem("Neu");
        MenuItem openItem = new MenuItem("Öffnen...");
        MenuItem exitItem = new MenuItem("Beenden");
        fileMenu.getItems().addAll(newItem, openItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().addAll(fileMenu);

        ToolBar toolbar = new ToolBar();
        Button selectButton = new Button("Auswählen");
        Button drawButton = new Button("Zeichnen");
        Button eraseButton = new Button("Löschen");
        toolbar.setOrientation(Orientation.VERTICAL);
        toolbar.getItems().addAll(selectButton, drawButton, eraseButton);

        setCenter(storageView);
        setLeft(toolbar);
        setTop(menuBar);

    }

}
