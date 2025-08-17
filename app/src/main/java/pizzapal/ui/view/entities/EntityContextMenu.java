package pizzapal.ui.view.entities;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.UnaryOperator;

import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.converter.FloatStringConverter;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Entity;
import pizzapal.model.domain.entities.SerializableColor;
import pizzapal.model.observability.ObservableField;
import pizzapal.model.reflection.ReflectionUtils;
import pizzapal.utils.Helper;

public class EntityContextMenu extends ContextMenu {

    private static final int WIDTH = 150;

    public EntityContextMenu(Entity entity, StorageController storageController) {

        for (Field field : ReflectionUtils.getEditableFields(entity.getClass())) {

            Type genericType = field.getGenericType();

            if (genericType instanceof ParameterizedType pt) {
                Type actualType = pt.getActualTypeArguments()[0];
                if (actualType == Float.class) {

                    field.setAccessible(true);
                    Object value = null;
                    try {
                        value = field.get(entity);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    ObservableField<?> obs = (ObservableField<?>) value;
                    Float f = (Float) obs.getValue();

                    TextFormatter<Float> textFormatter = floatFormatter(f);

                    VBox inputField = inputFieldWithLabel(Helper.capitalizeFirst(field.getName()), textFormatter);

                    CustomMenuItem item1 = new CustomMenuItem(inputField);
                    item1.setHideOnClick(false);

                    this.getItems().add(item1);
                } else if (actualType == SerializableColor.class) {

                    field.setAccessible(true);
                    Object value = null;
                    try {
                        value = field.get(entity);
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }

                    ObservableField<?> obs = (ObservableField<?>) value;
                    Color c = ((SerializableColor) obs.getValue()).getColor();

                    ColorPicker colorPicker = new ColorPicker(c);
                    setControlWidth(colorPicker);

                    colorPicker.setMaxWidth(150);
                    CustomMenuItem item1 = new CustomMenuItem(colorPicker);
                    item1.setHideOnClick(false);

                    this.getItems().add(item1);
                }

            }

        }

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(e -> {
            // storageController.edit(entity, Float.parseFloat(widthTextField.getText()),
            // // Float.parseFloat(heightTextField.getText()),
            // colorPicker.getValue());
            // ;
        });

        ImageView icon1 = new ImageView(Helper.loadImage("/icons/edit.png"));
        icon1.setFitWidth(16);
        icon1.setFitHeight(16);
        editItem.setGraphic(icon1);
        editItem.setStyle("-fx-font-weight: bold;");

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> {
            storageController.delete(entity);
        });

        ImageView icon2 = new ImageView(Helper.loadImage("/icons/trash.png"));
        icon2.setFitWidth(16);
        icon2.setFitHeight(16);
        deleteItem.setGraphic(icon2);
        deleteItem.setStyle("-fx-font-weight: bold;");

        this.getItems().addAll(editItem, new SeparatorMenuItem(), deleteItem);

    }

    private void setControlWidth(Control n) {
        n.setPrefWidth(WIDTH);
        n.setMaxWidth(WIDTH);
        n.setMinWidth(WIDTH);

    }

    public VBox inputFieldWithLabel(String labelText, TextFormatter<Float> formatter) {
        Label label = new Label(labelText);
        label.setFont(Font.font(10.5));
        TextField textField = new TextField();
        textField.setTextFormatter(formatter);
        textField.setMaxWidth(150);

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

}
