package pizzapal.ui.view.entities;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Entity;
import pizzapal.utils.Helper;

public class EntityContextMenu extends ContextMenu {

    private Entity entity;
    private StorageController storageController;

    private static final int WIDTH = 150;

    public EntityContextMenu(Entity entity, StorageController storageController) {
        TextField widthTextField = contextMenuTextField(String.valueOf(entity.getWidth()), "New Width",
                "newWidthField");
        entity.widthObservable().addListener((obs, oldValue, newValue) -> {
            widthTextField.setText(String.valueOf(newValue));
        });
        CustomMenuItem item1 = new CustomMenuItem(widthTextField);
        item1.setHideOnClick(false);

        TextField heightTextField = contextMenuTextField(String.valueOf(entity.getHeight()), "New Height",
                "newHeightField");
        entity.heightObservable().addListener((obs, oldValue, newValue) -> {
            heightTextField.setText(String.valueOf(newValue));
        });
        CustomMenuItem item2 = new CustomMenuItem(heightTextField);
        item2.setHideOnClick(false);

        ColorPicker colorPicker = new ColorPicker(entity.getColor().getColor());
        setControlWidth(colorPicker);
        entity.colorObservable().addListener((obs, oldValue, newValue) -> {
            colorPicker.setValue(newValue.getColor());
        });
        CustomMenuItem colorItem = new CustomMenuItem(colorPicker);
        colorItem.setHideOnClick(false);

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(e -> {
            storageController.edit(entity, Float.parseFloat(widthTextField.getText()),
                    Float.parseFloat(heightTextField.getText()),
                    colorPicker.getValue());
            ;
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

        this.getItems().addAll(item1, item2, colorItem, editItem, new SeparatorMenuItem(), deleteItem);

    }

    private void setControlWidth(Control n) {
        n.setPrefWidth(WIDTH);
        n.setMaxWidth(WIDTH);
        n.setMinWidth(WIDTH);

    }

    private TextField contextMenuTextField(String initialText, String promptText, String id) {
        TextField textField = new TextField();
        textField.setText(initialText);
        textField.setPromptText(promptText);
        textField.setId(id);
        setControlWidth(textField);

        return textField;
    }

    public void init() {

    }

}
