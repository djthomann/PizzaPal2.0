package pizzapal.ui.view.app.editor;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;

public class ToolBarView extends HBox {

    public ToolBarView() {

        // this.setOrientation(Orientation.HORIZONTAL);

        this.setPadding(new Insets(10));
        this.setSpacing(20);
        this.setAlignment(Pos.CENTER_LEFT);

        this.getChildren().addAll(supportInput(), separator(), boardInput(), separator(), itemInput());
    }

    public Separator separator() {
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setPrefHeight(30);

        return separator;
    }

    public HBox itemInput() {
        Button itemButton = new Button("Item");
        itemButton.setOnDragDetected(e -> {
            Dragboard db = itemButton.startDragAndDrop(TransferMode.COPY);
            db.setDragView(new WritableImage(1, 1));
            ClipboardContent content = new ClipboardContent();
            content.putString("ITEM");
            db.setContent(content);
            e.consume();
        });

        Label itemWidthLabel = new Label("W:");
        TextField itemWidthField = new TextField("100");
        itemWidthField.setMaxWidth(50);

        Label itemHeightLabel = new Label("W:");
        TextField itemHeightField = new TextField("100");
        itemHeightField.setMaxWidth(50);

        HBox itemInput = new HBox(itemButton, itemHeightLabel, itemHeightField, itemWidthLabel, itemWidthField);
        itemInput.setSpacing(5);
        itemInput.setAlignment(Pos.CENTER_LEFT);

        return itemInput;
    }

    public HBox supportInput() {
        Button supportButton = new Button("Support");
        supportButton.setOnDragDetected(e -> {
            Dragboard db = supportButton.startDragAndDrop(TransferMode.COPY);
            db.setDragView(new WritableImage(1, 1));
            ClipboardContent content = new ClipboardContent();
            content.putString("SUPPORT");
            db.setContent(content);
            e.consume();
        });

        Label supportWidthLabel = new Label("W:");
        TextField supportWidth = new TextField("100");
        supportWidth.setMaxWidth(50);

        Label supportHeightLabel = new Label("H:");
        TextField supportHeightField = new TextField("100");
        supportHeightField.setMaxWidth(50);

        HBox supportInput = new HBox(supportButton, supportWidthLabel, supportWidth, supportHeightLabel,
                supportHeightField);
        supportInput.setSpacing(5);
        supportInput.setAlignment(Pos.CENTER_LEFT);

        return supportInput;
    }

    public HBox boardInput() {
        ToggleButton boardButton = new ToggleButton("Board");
        boardButton.setOnDragDetected(e -> {
            Dragboard db = boardButton.startDragAndDrop(TransferMode.COPY);
            db.setDragView(new WritableImage(1, 1));
            ClipboardContent content = new ClipboardContent();
            content.putString("BOARD");
            db.setContent(content);
            e.consume();
        });

        Label boardHeightLabel = new Label("H:");

        TextField boardHeightInput = new TextField("100");
        boardHeightInput.setMaxWidth(50);

        HBox boardInput = new HBox(boardButton, boardHeightLabel, boardHeightInput);
        boardInput.setSpacing(5);
        boardInput.setAlignment(Pos.CENTER_LEFT);

        return boardInput;
    }

}
