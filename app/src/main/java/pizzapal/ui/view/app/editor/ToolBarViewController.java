package pizzapal.ui.view.app.editor;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextFormatter;
import pizzapal.ui.components.CustomToggleButton;
import pizzapal.utils.ToolState;
import pizzapal.utils.ToolState.Tool;

public class ToolBarViewController {

    private ToolBarView view;

    private static ToolState toolState;

    public ToolBarViewController(ToolState toolState) {
        view = new ToolBarView();
        this.toolState = toolState;

        initButtons();

        initFormatters();

        initColorPickers();
    }

    public static void selectSelectTool() {
        System.out.println("TOOL SELECT");
        toolState.setCurrentTool(Tool.SELECT);
    }

    public void initButtons() {

        CustomToggleButton selectButton = view.getSelectButton();
        selectButton.setOnMouseClicked(_ -> {
            selectSelectTool();
        });

        CustomToggleButton itemButton = view.getItemButton();
        itemButton.setOnMouseClicked(_ -> {
            toolState.setCurrentTool(Tool.ITEM);
        });

        CustomToggleButton supportButton = view.getSupportButton();
        supportButton.setOnMouseClicked(_ -> {
            toolState.setCurrentTool(Tool.SUPPORT);
        });

        CustomToggleButton boardButton = view.getBoardButton();
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

    public void initColorPickers() {
        ColorPicker boardColorPicker = view.getBoardColorPicker();
        boardColorPicker.valueProperty().addListener((_, _, newColor) -> {
            toolState.setBoardColor(newColor);
        });

        ColorPicker supportColorPicker = view.getSupportColorPicker();
        supportColorPicker.valueProperty().addListener((_, _, newColor) -> {
            toolState.setSupportColor(newColor);
        });
    }

    public ToolBarView getView() {
        return view;
    }

}
