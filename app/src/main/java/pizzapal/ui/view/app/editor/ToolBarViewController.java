package pizzapal.ui.view.app.editor;

import javafx.scene.control.Button;
import javafx.scene.control.TextFormatter;
import pizzapal.utils.ToolState;
import pizzapal.utils.ToolState.Tool;

public class ToolBarViewController {

    private ToolBarView view;

    private ToolState toolState;

    public ToolBarViewController(ToolState toolState) {
        view = new ToolBarView();
        this.toolState = toolState;

        initButtons();

        initFormatters();
    }

    public void initButtons() {

        Button selectButton = view.getSelectButton();
        selectButton.setOnMouseClicked(_ -> {
            toolState.setCurrentTool(Tool.SELECT);
        });

        Button itemButton = view.getItemButton();
        itemButton.setOnMouseClicked(_ -> {
            toolState.setCurrentTool(Tool.ITEM);
        });

        Button supportButton = view.getSupportButton();
        supportButton.setOnMouseClicked(_ -> {
            toolState.setCurrentTool(Tool.SUPPORT);
        });

        Button boardButton = view.getBoardButton();
        boardButton.setOnMouseClicked(_ -> {
            toolState.setCurrentTool(Tool.BOARD);
        });
    }

    public void initFormatters() {
        TextFormatter<Float> supportWidthFormatter = view.getSupportWidthFormatter();
        supportWidthFormatter.valueProperty().addListener((_, _, newValue) -> {
            toolState.setSupportWidth(newValue);
        });

        TextFormatter<Float> suppotHeightFormatter = view.getSupportHeightFormatter();
        suppotHeightFormatter.valueProperty().addListener((_, _, newValue) -> {
            toolState.setSupportHeight(newValue);
        });

        TextFormatter<Float> boardHeightFormatter = view.getBoardHeightFormatter();
        boardHeightFormatter.valueProperty().addListener((_, _, newValue) -> {
            toolState.setBoardHeight(newValue);
        });

        TextFormatter<Float> itemWidthFormatter = view.getItemWidthFormatter();
        itemWidthFormatter.valueProperty().addListener((_, _, newValue) -> {
            System.out.println(newValue);
            toolState.setItemWidth(newValue);
        });

        TextFormatter<Float> itemHeightFormatter = view.getItemHeightFormatter();
        itemHeightFormatter.valueProperty().addListener((_, _, newValue) -> {
            System.out.println(newValue);
            toolState.setItemHeight(newValue);
        });

    }

    public ToolBarView getView() {
        return view;
    }

}
