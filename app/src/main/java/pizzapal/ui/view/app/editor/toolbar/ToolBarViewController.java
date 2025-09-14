package pizzapal.ui.view.app.editor.toolbar;

import java.lang.reflect.Field;
import java.util.Map.Entry;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextFormatter;
import pizzapal.model.domain.entities.SerializableColor;
import pizzapal.utils.EntityField;
import pizzapal.utils.ToolState;
import pizzapal.utils.ToolState.Tool;

public class ToolBarViewController {

    // TODO bilaterally connect ToolBarView with ToolState

    private ToolBarView view;

    private static ToolState toolState;

    // private IngredientRepository ingredientRepository;

    public ToolBarViewController(ToolState toolState) {
        view = new ToolBarView();
        // ingredientRepository = IngredientRepository.getInstance();
        ToolBarViewController.toolState = toolState;

        for (EntityInput i : view.getEntityInputs()) {
            i.setToggleGroup(view.getButtonGroup());
            i.getButton().setOnAction(e -> {
                toolState.setCurrentToolByEntity(i.getEntityClass());
            });
            for (Entry<Field, TextFormatter<?>> e : i.getTextFormatters()) {

                toolState.putValue(new EntityField(i.getEntityClass(), e.getKey()), e.getValue().getValue());
                e.getValue().valueProperty().addListener((obs, oldValue, newValue) -> {
                    toolState.putValue(new EntityField(i.getEntityClass(), e.getKey()), e.getValue().getValue());
                });
            }
            Entry<Field, ColorPicker> colorPickerEntry = i.getColorPickerEntry();
            toolState.putValue(new EntityField(i.getEntityClass(), colorPickerEntry.getKey()),
                    new SerializableColor(colorPickerEntry.getValue().getValue()));
            colorPickerEntry.getValue().valueProperty().addListener((obs, oldValue, newValue) -> {
                toolState.putValue(new EntityField(i.getEntityClass(), colorPickerEntry.getKey()),
                        new SerializableColor(newValue));
            });
        }

        toolState.setInitialised();

        view.getSelectButton().setOnAction(e -> {
            toolState.setCurrentTool(Tool.SELECT);
        });
        // initComboBox();

    }

    public static void selectSelectTool() {
        toolState.setCurrentTool(Tool.SELECT);
    }

    // public void initComboBox() {
    // ComboBox<String> comboBox = view.getIngredientComboBox();
    // ObservableList<String> items = comboBox.getItems();
    // Set<String> ingredients = ingredientRepository.getAllIngredientNames();
    // items.addAll(ingredients);

    // comboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
    // toolState.setItemIngredient(ingredientRepository.getIngredient(newValue));
    // });
    // }

    public ToolBarView getView() {
        return view;
    }

}
