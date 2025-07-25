package pizzapal.ui.toolbar;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pizzapal.model.storage.StorageController;

public class ToolbarView extends HBox {

    public ToolbarView(StorageController storageController) {

        this.setPrefHeight(100f);
        this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        Button buttonRedo = new Button("Redo");
        buttonRedo.setOnMouseClicked(e -> {
            storageController.redo();
        });

        Button buttonUndo = new Button("Undo");
        buttonUndo.setOnMouseClicked(e -> {
            storageController.undo();
        });

        this.setAlignment(Pos.CENTER_RIGHT);

        this.getChildren().addAll(buttonUndo, buttonRedo);

    }

}
