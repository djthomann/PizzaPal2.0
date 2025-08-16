package pizzapal.ui.view.app.editor.toolbar;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.UnaryOperator;

import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.converter.FloatStringConverter;
import pizzapal.model.domain.entities.Entity;
import pizzapal.model.domain.entities.SerializableColor;
import pizzapal.model.reflection.ReflectionUtils;
import pizzapal.ui.components.CustomToggleButton;
import pizzapal.utils.Config;
import pizzapal.utils.EntityField;
import pizzapal.utils.Helper;

public class EntityInput extends HBox {

    private final Class<? extends Entity> entityClass;

    CustomToggleButton button;

    ColorPicker colorPicker;

    private Map<Field, TextFormatter<?>> textFormatterMap = new HashMap<>();

    private Map.Entry<Field, ColorPicker> colorPickerEntry;

    public EntityInput(Class<? extends Entity> clazz) {

        entityClass = clazz;

        button = new CustomToggleButton(clazz.getSimpleName());

        this.getChildren().add(button);
        this.setSpacing(5);
        this.setAlignment(Pos.BOTTOM_LEFT);

        for (Field field : ReflectionUtils.getEditableFields(clazz)) {

            Type genericType = field.getGenericType();

            if (genericType instanceof ParameterizedType pt) {
                Type actualType = pt.getActualTypeArguments()[0];
                if (actualType == Float.class) {

                    TextFormatter<Float> textFormatter = floatFormatter(
                            Config.getDefaultSize(new EntityField(clazz, field)));
                    textFormatterMap.put(field, textFormatter);

                    this.getChildren()
                            .add(inputFieldWithLabel(Helper.capitalizeFirst(field.getName()), textFormatter));
                } else if (actualType == SerializableColor.class) {
                    colorPicker = new ColorPicker(Config.getDefaultColor(new EntityField(clazz, field)));
                    colorPickerEntry = new AbstractMap.SimpleEntry<>(field, colorPicker);
                    colorPicker.setMaxWidth(50);
                    this.getChildren().addAll(colorPicker);
                }

            }

        }

    }

    public VBox inputFieldWithLabel(String labelText, TextFormatter<Float> formatter) {
        Label label = new Label(labelText);
        label.setFont(Font.font(10.5));
        TextField textField = new TextField();
        textField.setTextFormatter(formatter);
        textField.setMaxWidth(50);

        VBox box = new VBox(label, textField);
        box.setAlignment(Pos.BOTTOM_LEFT);

        return box;
    }

    public TextFormatter<Float> floatFormatter(float defaultValue) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?\\d*(\\.\\d*)?")) {
                return change;
            }
            return null;
        };

        TextFormatter<Float> floatFormatter = new TextFormatter<>(
                new FloatStringConverter(),
                defaultValue,
                filter);

        return floatFormatter;
    }

    public Set<Entry<Field, TextFormatter<?>>> getTextFormatters() {
        return textFormatterMap.entrySet();
    }

    public Map.Entry<Field, ColorPicker> getColorPickerEntry() {
        return colorPickerEntry;
    }

    public CustomToggleButton getButton() {
        return button;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public void setToggleGroup(ToggleGroup group) {
        button.setToggleGroup(group);
    }

    public Class<? extends Entity> getEntityClass() {
        return entityClass;
    }

}
