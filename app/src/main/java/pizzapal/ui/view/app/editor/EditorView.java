package pizzapal.ui.view.app.editor;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pizzapal.utils.SceneManager;

public class EditorView extends BorderPane {

    private MenuBarView menuBar;
    private ToolBarView toolBar;
    private TabPane tabPane;

    private VBox topContainer;

    public EditorView() {

        tabPane = new TabPane();

        menuBar = new MenuBarView();

        topContainer = new VBox(menuBar);

        setCenter(tabPane);
        setTop(topContainer);
        setBottom(new NotificationBarView());

    }

    public void addTabPane(TabPane pane) {
        setCenter(pane);
    }

    public void addToolBar(ToolBarView toolBar) {
        this.toolBar = toolBar;
        if (!topContainer.getChildren().contains(toolBar)) {
            topContainer.getChildren().add(1, toolBar);
        } else {
            topContainer.getChildren().set(1, toolBar);
        }
    }

    public MenuBarView getMenuBar() {
        return menuBar;
    }

    public ToolBarView getToolBar() {
        return toolBar;
    }

    public boolean isToolBarVisible() {
        return topContainer.getChildren().contains(toolBar);
    }

    public void hideToolBar() {
        topContainer.getChildren().remove(toolBar);
        SceneManager.getInstance().sizeStage();
    }

    public void showToolBar() {
        topContainer.getChildren().add(toolBar);
        SceneManager.getInstance().sizeStage();
    }

    public TabPane getTabPane() {
        return tabPane;
    }

    public Tab getSelectedTab() {
        return tabPane.getSelectionModel().getSelectedItem();
    }

}
