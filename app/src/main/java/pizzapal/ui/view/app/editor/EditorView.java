package pizzapal.ui.view.app.editor;

import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import pizzapal.utils.SceneManager;

public class EditorView extends BorderPane {

    private MenuBarView menuBar;
    private Separator separator;
    private ToolBarView toolBar;
    private TabPane tabPane;

    private VBox topContainer;

    public EditorView() {

        tabPane = new TabPane();

        menuBar = new MenuBarView();

        separator = new Separator();
        separator.setOrientation(Orientation.HORIZONTAL);

        topContainer = new VBox(menuBar, separator);

        setCenter(tabPane);
        setTop(topContainer);

    }

    public void addTabPane(TabPane pane) {
        setCenter(pane);
    }

    public void addToolBar(ToolBarView toolBar) {
        this.toolBar = toolBar;
        if (!topContainer.getChildren().contains(toolBar)) {
            topContainer.getChildren().add(2, toolBar);
        } else {
            topContainer.getChildren().set(2, toolBar);
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
        topContainer.getChildren().removeAll(separator, toolBar);
        SceneManager.getInstance().sizeStage();
    }

    public void showToolBar() {
        topContainer.getChildren().addAll(separator, toolBar);
        SceneManager.getInstance().sizeStage();
    }

    public TabPane getTabPane() {
        return tabPane;
    }

}
