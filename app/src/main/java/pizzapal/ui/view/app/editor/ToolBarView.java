package pizzapal.ui.view.app.editor;

import java.util.function.UnaryOperator;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.converter.FloatStringConverter;
import pizzapal.ui.components.CustomToggleButton;
import pizzapal.utils.ToolState;

public class ToolBarView extends HBox {

    private ToggleGroup buttonGroup;

    private CustomToggleButton selectButton;

    private CustomToggleButton colorPipette;

    private CustomToggleButton itemButton;
    private TextFormatter<Float> itemWidthFormatter;
    private TextFormatter<Float> itemHeightFormatter;

    private CustomToggleButton supportButton;
    private TextFormatter<Float> supportWidthFormatter;
    private TextFormatter<Float> supportHeightFormatter;
    private ColorPicker supportColorPicker;

    private CustomToggleButton boardButton;
    private TextFormatter<Float> boardHeightFormatter;
    private ColorPicker boardColorPicker;

    public ToolBarView() {

        this.setPadding(new Insets(10));
        this.setSpacing(10);
        this.setAlignment(Pos.BOTTOM_LEFT);

        buttonGroup = new ToggleGroup();

        selectButton = new CustomToggleButton("Select");
        selectButton.setToggleGroup(buttonGroup);

        this.getChildren().addAll(selectButton, separator(), supportInput(), separator(), boardInput(), separator(),
                itemInput());
    }

    public Separator separator() {
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setPrefHeight(30);

        return separator;
    }

    public VBox inputFieldWithLabel(String labelText, TextFormatter<Float> formatter) {
        Label label = new Label(labelText);
        TextField textField = new TextField();
        textField.getStylesheets().add(getClass().getResource("/styles/textfield.css").toExternalForm());
        textField.getStyleClass().add("custom-textfield");
        textField.setTextFormatter(formatter);
        textField.setMaxWidth(50);

        return new VBox(label, textField);
    }

    public HBox itemInput() {
        itemButton = new CustomToggleButton("Item");
        itemButton.setToggleGroup(buttonGroup);

        itemWidthFormatter = floatFormatter(ToolState.STANDARD_ITEM_WIDTH);
        VBox itemWidthField = inputFieldWithLabel("Width:", itemWidthFormatter);

        itemHeightFormatter = floatFormatter(ToolState.STANDARD_ITEM_HEIGHT);
        VBox itemHeightField = inputFieldWithLabel("Height:", itemHeightFormatter);

        HBox itemInput = new HBox(itemButton, itemWidthField, itemHeightField);
        itemInput.setSpacing(5);
        itemInput.setAlignment(Pos.BOTTOM_LEFT);

        return itemInput;
    }

    public HBox supportInput() {
        supportButton = new CustomToggleButton("Support");
        supportButton.setToggleGroup(buttonGroup);

        supportWidthFormatter = floatFormatter(ToolState.STANDARD_SUPPORT_WIDTH);
        VBox supportWidthField = inputFieldWithLabel("Width:", supportWidthFormatter);

        supportHeightFormatter = floatFormatter(ToolState.STANDARD_SUPPORT_HEIGHT);
        VBox supportHeightField = inputFieldWithLabel("Height:", supportHeightFormatter);

        supportColorPicker = new ColorPicker(ToolState.STANDARD_SUPPORT_COLOR);
        supportColorPicker.setMaxWidth(50);

        HBox supportInput = new HBox(supportButton, supportWidthField, supportHeightField, supportColorPicker);
        supportInput.setSpacing(5);
        supportInput.setAlignment(Pos.BOTTOM_LEFT);

        return supportInput;
    }

    public HBox boardInput() {
        boardButton = new CustomToggleButton("Board");
        boardButton.setToggleGroup(buttonGroup);

        boardHeightFormatter = floatFormatter(ToolState.STANDARD_BOARD_HEIGHT);
        VBox boardHeightField = inputFieldWithLabel("Height:", boardHeightFormatter);

        boardColorPicker = new ColorPicker(ToolState.STANDARD_BOARD_COLOR);
        boardColorPicker.setMaxWidth(50);

        HBox boardInput = new HBox(boardButton, boardHeightField, boardColorPicker);
        boardInput.setSpacing(5);
        boardInput.setAlignment(Pos.BOTTOM_LEFT);

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

    public CustomToggleButton getSelectButton() {
        return selectButton;
    }

    public CustomToggleButton getItemButton() {
        return itemButton;
    }

    public CustomToggleButton getSupportButton() {
        return supportButton;
    }

    public CustomToggleButton getBoardButton() {
        return boardButton;
    }

    public ColorPicker getBoardColorPicker() {
        return boardColorPicker;
    }

    public ColorPicker getSupportColorPicker() {
        return supportColorPicker;
    }

}
