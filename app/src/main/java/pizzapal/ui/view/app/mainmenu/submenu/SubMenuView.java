package pizzapal.ui.view.app.mainmenu.submenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import pizzapal.ui.UIConfig;
import pizzapal.ui.components.TextButton;

public abstract class SubMenuView extends VBox {

    TextButton backButton;

    VBox controlsBox;

    protected SubMenuView(String title, String subtitle) {

        setPrefSize(1000, 700);
        setMinSize(1000, 700);
        setMaxSize(1000, 700);

        Label labelTitle = new Label(title);
        labelTitle.setFont(UIConfig.EXTRA_LARGE_BOLD_FONT);

        Label labelSubtitle = new Label(subtitle);
        labelSubtitle.setFont(UIConfig.NORMAL_MEDIUM_FONT);

        backButton = new TextButton("Back to Main Menu");

        controlsBox = new VBox();
        controlsBox.setSpacing(20);
        controlsBox.setAlignment(Pos.CENTER_RIGHT);
        VBox.setVgrow(controlsBox, Priority.ALWAYS);

        this.setAlignment(Pos.BOTTOM_RIGHT);
        this.setPadding(new Insets(60));
        this.getChildren().addAll(labelTitle, labelSubtitle, controlsBox, backButton);

    }

    public void addControlOnTop(Node n) {
        controlsBox.getChildren().add(0, n);
    }

    public TextButton getBackButton() {
        return backButton;
    }

}
