package pizzapal.ui.view.app.mainmenu.settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import pizzapal.ui.UIConfig;
import pizzapal.ui.components.TextButton;

public class SettingsView extends VBox {

    TextButton backButton;

    public SettingsView() {

        setPrefSize(1000, 700);
        setMinSize(1000, 700);
        setMaxSize(1000, 700);

        Label labelTitle = new Label("Settings");
        labelTitle.setFont(UIConfig.EXTRA_LARGE_BOLD_FONT);

        Label labelDescription = new Label("Configure PizzaPal the way you like it!");
        labelDescription.setFont(UIConfig.NORMAL_MEDIUM_FONT);

        backButton = new TextButton("Back to Main Menu");

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.getChildren().add(backButton);

        this.setAlignment(Pos.BOTTOM_LEFT);
        this.setPadding(new Insets(60));
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        this.getChildren().addAll(labelTitle, labelDescription, spacer, buttonBox);

    }

    public TextButton getBackButton() {
        return backButton;
    }

}
