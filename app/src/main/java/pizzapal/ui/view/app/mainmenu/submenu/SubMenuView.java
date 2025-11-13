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

    public enum SubMenuPosition {
        TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
    }

    protected final SubMenuPosition position;

    protected Label titleLabel;
    protected Label subtitleLabel;

    protected TextButton backButton;
    protected VBox controlsBox;

    protected SubMenuView(String title, String subtitle, SubMenuPosition position) {

        this.position = position;

        setPrefSize(1000, 700);
        setMinSize(1000, 700);
        setMaxSize(1000, 700);

        titleLabel = new Label(title);
        titleLabel.setFont(UIConfig.EXTRA_LARGE_BOLD_FONT);

        subtitleLabel = new Label(subtitle);
        subtitleLabel.setFont(UIConfig.NORMAL_MEDIUM_FONT);

        backButton = new TextButton("Back to Main Menu");

        controlsBox = new VBox();
        controlsBox.setSpacing(20);
        VBox.setVgrow(controlsBox, Priority.ALWAYS);

        switch (position) {
            case TOP_RIGHT -> {
                this.setAlignment(Pos.BOTTOM_RIGHT);
                controlsBox.setAlignment(Pos.CENTER_RIGHT);
            }
            case BOTTOM_LEFT -> {
                this.setAlignment(Pos.BOTTOM_LEFT);
                controlsBox.setAlignment(Pos.CENTER_LEFT);
            }
            case BOTTOM_RIGHT -> {
                this.setAlignment(Pos.BOTTOM_RIGHT);
                controlsBox.setAlignment(Pos.CENTER_RIGHT);
            }
        }
        this.setPadding(new Insets(60));
        this.getChildren().addAll(titleLabel, subtitleLabel, controlsBox, backButton);

    }

    public void onShow() {

    }

    public void addControlOnTop(Node n) {
        controlsBox.getChildren().add(0, n);
    }

    public TextButton getBackButton() {
        return backButton;
    }

    public SubMenuPosition getPosition() {
        return position;
    }

}
