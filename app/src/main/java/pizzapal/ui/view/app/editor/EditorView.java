package pizzapal.ui.view.app.editor;

import java.util.HashMap;
import java.util.Map;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;

public class EditorView extends BorderPane {

    private Map<Tab, StorageController> controllerMap;

    private MenuBarView menuBar;
    private Separator separator;
    private ToolBarView toolBar;
    private TabPane tabPane;

    private VBox vBox;

    public EditorView(Storage storage) {

        controllerMap = new HashMap<>();

        tabPane = new TabPane();

        menuBar = new MenuBarView(controllerMap, tabPane);
        toolBar = new ToolBarView();

        separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);

        vBox = new VBox(menuBar, separator, toolBar);
        setCenter(tabPane);
        setTop(vBox);

    }

    public MenuBarView getMenuBar() {
        return menuBar;
    }

    public ToolBarView getToolBar() {
        return toolBar;
    }

    public boolean isToolBarVisible() {
        return vBox.getChildren().contains(toolBar);
    }

    public void hideToolBar() {
        vBox.getChildren().removeAll(separator, toolBar);
    }

    public void showToolBar() {
        vBox.getChildren().addAll(separator, toolBar);
    }

    public TabPane getTabPane() {
        return tabPane;
    }

}
