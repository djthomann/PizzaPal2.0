package pizzapal.ui.view.entities;

import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Entity;
import pizzapal.utils.Helper;

public class EntityContextMenu extends ContextMenu {

    private Entity entity;
    private StorageController storageController;

    public EntityContextMenu(Entity entity, StorageController storageController) {
        TextField widthTextField = new TextField();
        widthTextField.setText(String.valueOf(entity.getWidth()));
        entity.widthObservable().addListener((obs, oldValue, newValue) -> {
            widthTextField.setText(String.valueOf(newValue));
        });
        widthTextField.setPrefWidth(150);
        widthTextField.setMaxWidth(150);
        widthTextField.setPromptText("New Width");
        widthTextField.setId("newWidthField");
        CustomMenuItem item1 = new CustomMenuItem(widthTextField);
        item1.setHideOnClick(false);

        TextField heightTextField = new TextField();
        heightTextField.setText(String.valueOf(entity.getHeight()));
        entity.heightObservable().addListener((obs, oldValue, newValue) -> {
            heightTextField.setText(String.valueOf(newValue));
        });
        heightTextField.setPrefWidth(150);
        heightTextField.setMaxWidth(150);
        heightTextField.setPromptText("New Height");
        heightTextField.setId("newHeightField");
        CustomMenuItem item2 = new CustomMenuItem(heightTextField);
        item2.setHideOnClick(false);

        ColorPicker colorPicker = new ColorPicker(Color.RED);
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

    public void init() {

    }

}
