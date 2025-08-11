package pizzapal.ui.view.app.editor;

import java.util.Set;

import javafx.collections.ObservableList;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextFormatter;
import pizzapal.model.repository.IngredientRepository;
import pizzapal.ui.components.CustomToggleButton;
import pizzapal.utils.ToolState;
import pizzapal.utils.ToolState.Tool;

public class ToolBarViewController {

    // TODO bilateral connect ToolBarView with ToolState

    private ToolBarView view;

    private static ToolState toolState;

    private IngredientRepository ingredientRepository;

    public ToolBarViewController(ToolState toolState) {
        view = new ToolBarView();
        ingredientRepository = IngredientRepository.getInstance();
        ToolBarViewController.toolState = toolState;

        initButtons();

        initFormatters();

        initComboBox();

        initColorPickers();
    }

    public static void selectSelectTool() {
        toolState.setCurrentTool(Tool.SELECT);
    }

    public void initComboBox() {
        ComboBox<String> comboBox = view.getIngredientComboBox();
        ObservableList<String> items = comboBox.getItems();
        Set<String> ingredients = ingredientRepository.getAllIngredientNames();
        items.addAll(ingredients);

        comboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            toolState.setItemIngredient(ingredientRepository.getIngredient(newValue));
        });
    }

    public void initButtons() {

        CustomToggleButton selectButton = view.getSelectButton();
        selectButton.setOnMouseClicked(e -> {
            selectSelectTool();
        });

        CustomToggleButton pickColorButton = view.getPickColorButton();
        pickColorButton.setOnMouseClicked(e -> {
            toolState.setCurrentTool(Tool.PICKCOLOR);
        });

        CustomToggleButton itemButton = view.getItemButton();
        itemButton.setOnMouseClicked(e -> {
            toolState.setCurrentTool(Tool.ITEM);
        });

        CustomToggleButton supportButton = view.getSupportButton();
        supportButton.setOnMouseClicked(e -> {
            toolState.setCurrentTool(Tool.SUPPORT);
        });

        CustomToggleButton boardButton = view.getBoardButton();
        boardButton.setOnMouseClicked(e -> {
            toolState.setCurrentTool(Tool.BOARD);
        });
    }

    public void initFormatters() {
        TextFormatter<Float> supportWidthFormatter = view.getSupportWidthFormatter();
        supportWidthFormatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            toolState.setSupportWidth(newValue);
        });

        TextFormatter<Float> suppotHeightFormatter = view.getSupportHeightFormatter();
        suppotHeightFormatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            toolState.setSupportHeight(newValue);
        });

        TextFormatter<Float> boardHeightFormatter = view.getBoardHeightFormatter();
        boardHeightFormatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            toolState.setBoardHeight(newValue);
        });

        TextFormatter<Float> itemWidthFormatter = view.getItemWidthFormatter();
        itemWidthFormatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            toolState.setItemWidth(newValue);
        });

        TextFormatter<Float> itemHeightFormatter = view.getItemHeightFormatter();
        itemHeightFormatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            toolState.setItemHeight(newValue);
        });

        TextFormatter<Float> itemWeightFormatter = view.getItemWeightFormatter();
        itemWeightFormatter.valueProperty().addListener((obs, oldValue, newValue) -> {
            toolState.setItemWeight(newValue);
        });

    }

    public void initColorPickers() {
        ColorPicker boardColorPicker = view.getBoardColorPicker();
        boardColorPicker.valueProperty().addListener((obs, oldValue, newColor) -> {
            toolState.setBoardColor(newColor);
        });

        ColorPicker supportColorPicker = view.getSupportColorPicker();
        supportColorPicker.valueProperty().addListener((obs, oldValue, newColor) -> {
            toolState.setSupportColor(newColor);
        });
    }

    public ToolBarView getView() {
        return view;
    }

}
