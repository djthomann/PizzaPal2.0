package pizzapal.ui.components;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import pizzapal.ui.UIConfig;
import pizzapal.utils.NotificationManager;

public class NotificationDropdown {

    private final Popup popup;

    private final VBox contentBox;

    private Button bellButton;

    private List<String> notifications = new ArrayList<>();

    // TODO: Improve
    public NotificationDropdown() {

        popup = new Popup();
        popup.setAutoHide(true);

        contentBox = new VBox();
        contentBox.setStyle("""
                    -fx-background-color: -fx-background;
                    -fx-border-color: black;
                    -fx-border-width: 1;
                    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 8, 0.1, 0, 2);
                """);

        // really inefficient!
        NotificationManager.getInstance().addListener(newNotifications -> {
            notifications = newNotifications;
            Platform.runLater(() -> {
                contentBox.getChildren().clear();

                contentBox.getChildren().addAll(header());

                for (String n : newNotifications) {
                    HBox h = new HBox();
                    Label l = new Label(n);
                    IconButton b = new IconButton("/icons/check.png");
                    b.setImageSize(16, 16);
                    b.setOnAction(e -> {
                        NotificationManager.getInstance().removeNotification(n);
                    });
                    Region spacer = new Region();
                    HBox.setHgrow(spacer, Priority.ALWAYS);
                    h.setPadding(new Insets(5));
                    h.getChildren().addAll(l, spacer, b);
                    h.setAlignment(Pos.CENTER_RIGHT);
                    contentBox.getChildren().addAll(h);
                }
            });
            if (newNotifications.isEmpty()) {
                popup.hide();
            }
        });

        Platform.runLater(() -> {
            contentBox.layoutBoundsProperty().addListener((obs, oldBounds, newBounds) -> {
                positionPopup(newBounds.getHeight(), contentBox.getWidth());
            });
        });

        popup.getContent().add(contentBox);

        bellButton = new IconButton("/icons/bell.png");
        bellButton.setFocusTraversable(false);
        bellButton.setOnAction(_ -> toggle());
        hide();

    }

    public HBox header() {
        HBox hBox = new HBox();
        Label label = new Label("Notifications");
        label.setFont(UIConfig.SMALL_NORMAL_BOLD_FONT);
        IconButton button = new IconButton("/icons/clear.png");
        button.setOnAction(_ -> NotificationManager.getInstance().clearNotifications());
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        hBox.setBackground(UIConfig.APP_BACKGROUND_DARKER);
        hBox.setPadding(new Insets(5));
        hBox.setMinWidth(300);
        hBox.getChildren().addAll(label, spacer, button);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    public void hide() {
        bellButton.setVisible(false);
    }

    public void show() {
        bellButton.setVisible(true);
    }

    public Button getBellButton() {
        return bellButton;
    }

    private void toggle() {
        if (popup.isShowing()) {
            popup.hide();
        } else if (!notifications.isEmpty()) {
            popup.show(bellButton, 0, 0);
            Platform.runLater(() -> {
                positionPopup(contentBox.getHeight(), contentBox.getWidth());
            });
        }
    }

    private void positionPopup(double popHeight, double popWidth) {
        double buttonRightX = bellButton.localToScreen(bellButton.getWidth(), 0).getX();
        double popupX = buttonRightX - popWidth - 10;

        double buttonTopY = bellButton.localToScreen(0, 0).getY();
        double popupY = buttonTopY - popHeight - 10;

        popup.setX(popupX);
        popup.setY(popupY);
    }

}
