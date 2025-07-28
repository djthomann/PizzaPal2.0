package pizzapal.ui.view.app.editor;

import java.util.function.UnaryOperator;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.util.converter.FloatStringConverter;
import pizzapal.utils.ToolState;

public class ToolBarView extends HBox {

    private Button selectButton;

    private Button itemButton;
    private TextFormatter<Float> itemWidthFormatter;
    private TextFormatter<Float> itemHeightFormatter;

    private Button supportButton;
    private TextFormatter<Float> supportWidthFormatter;
    private TextFormatter<Float> supportHeightFormatter;

    private Button boardButton;
    private TextFormatter<Float> boardHeightFormatter;

    public ToolBarView() {

        this.setPadding(new Insets(10));
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER_LEFT);

        selectButton = new Button("Select");

        this.getChildren().addAll(selectButton, separator(), supportInput(), separator(), boardInput(), separator(),
                itemInput());
    }

    public Separator separator() {
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setPrefHeight(30);

        return separator;
    }

    public HBox itemInput() {
        itemButton = new Button("Item");

        Label itemWidthLabel = new Label("W:");
        TextField itemWidthField = new TextField();
        itemWidthFormatter = floatFormatter(ToolState.STANDARD_ITEM_WIDTH);
        itemWidthField.setTextFormatter(itemWidthFormatter);
        itemWidthField.setMaxWidth(50);

        Label itemHeightLabel = new Label("H:");
        TextField itemHeightField = new TextField();
        itemHeightFormatter = floatFormatter(ToolState.STANDARD_ITEM_HEIGHT);
        itemHeightField.setTextFormatter(itemHeightFormatter);
        itemHeightField.setMaxWidth(50);

        HBox itemInput = new HBox(itemButton, itemWidthLabel, itemWidthField, itemHeightLabel, itemHeightField);
        itemInput.setSpacing(5);
        itemInput.setAlignment(Pos.CENTER_LEFT);

        return itemInput;
    }

    public HBox supportInput() {
        supportButton = new Button("Support");

        Label supportWidthLabel = new Label("W:");
        TextField supportWidthField = new TextField();
        supportWidthFormatter = floatFormatter(ToolState.STANDARD_SUPPORT_WIDTH);
        supportWidthField.setTextFormatter(supportWidthFormatter);
        supportWidthField.setMaxWidth(50);

        Label supportHeightLabel = new Label("H:");
        TextField supportHeightField = new TextField();
        supportHeightFormatter = floatFormatter(ToolState.STANDARD_SUPPORT_HEIGHT);
        supportHeightField.setTextFormatter(supportHeightFormatter);
        supportHeightField.setMaxWidth(50);

        HBox supportInput = new HBox(supportButton, supportWidthLabel, supportWidthField, supportHeightLabel,
                supportHeightField);
        supportInput.setSpacing(5);
        supportInput.setAlignment(Pos.CENTER_LEFT);

        return supportInput;
    }

    public HBox boardInput() {
        boardButton = new Button("Board");

        Label boardHeightLabel = new Label("H:");

        TextField boardHeightInput = new TextField();
        boardHeightFormatter = floatFormatter(ToolState.STANDARD_BOARD_HEIGHT);
        boardHeightInput.setTextFormatter(boardHeightFormatter);
        boardHeightInput.setMaxWidth(50);

        HBox boardInput = new HBox(boardButton, boardHeightLabel, boardHeightInput);
        boardInput.setSpacing(5);
        boardInput.setAlignment(Pos.CENTER_LEFT);

        return boardInput;
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

    public TextFormatter<Float> getSupportWidthFormatter() {
        return supportWidthFormatter;
    }

    public TextFormatter<Float> getSupportHeightFormatter() {
        return supportHeightFormatter;
    }

    public TextFormatter<Float> getBoardHeightFormatter() {
        return boardHeightFormatter;
    }

    public TextFormatter<Float> getItemWidthFormatter() {
        return itemWidthFormatter;
    }

    public TextFormatter<Float> getItemHeightFormatter() {
        return itemHeightFormatter;
    }

    public Button getSelectButton() {
        return selectButton;
    }

    public Button getItemButton() {
        return itemButton;
    }

    public Button getSupportButton() {
        return supportButton;
    }

    public Button getBoardButton() {
        return boardButton;
    }

}
