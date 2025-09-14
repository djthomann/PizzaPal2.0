package pizzapal.ui.view.app.editor.toolbar;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.components.CustomToggleButton;

public class ToolBarView extends HBox {

    private ToggleGroup buttonGroup;

    private CustomToggleButton selectButton;

    // private CustomToggleButton pickColorButton;

    private List<EntityInput> entityInputs;

    public ToolBarView() {

        this.setPadding(new Insets(10));
        this.setSpacing(10);
        this.setAlignment(Pos.BOTTOM_LEFT);

        buttonGroup = new ToggleGroup();

        selectButton = new CustomToggleButton("Select");
        selectButton.setToggleGroup(buttonGroup);

        // pickColorButton = new CustomToggleButton("Pick");
        // pickColorButton.setToggleGroup(buttonGroup);

        EntityInput supportInput = new EntityInput(Support.class);
        EntityInput boardInput = new EntityInput(Board.class);
        EntityInput itemInput = new EntityInput(Item.class);

        entityInputs = List.of(supportInput, boardInput, itemInput);

        this.getChildren().addAll(selectButton, /* pickColorButton, */ separator(),
                supportInput, separator(),
                boardInput, separator(),
                itemInput);
    }

    public Separator separator() {
        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setPrefHeight(30);

        return separator;
    }

    public CustomToggleButton getSelectButton() {
        return selectButton;
    }

    public ToggleGroup getButtonGroup() {
        return buttonGroup;
    }

    public List<EntityInput> getEntityInputs() {
        return entityInputs;
    }

}
