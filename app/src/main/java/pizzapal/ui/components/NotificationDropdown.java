package pizzapal.ui.components;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import pizzapal.NotificationManager;

public class NotificationDropdown {

    private final Popup popup;

    private final VBox contentBox;

    private final Button bellButton;

    private List<String> notifications = new ArrayList<>();

    // TODO: Improve
    public NotificationDropdown() {

        popup = new Popup();
        popup.setAutoHide(true);

        contentBox = new VBox();
        contentBox.setSpacing(10);
        contentBox.setStyle("""
                    -fx-background-color: -fx-background;
                    -fx-padding: 5;
                    -fx-border-color: black;
                    -fx-border-width: 1;
                    -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 8, 0.1, 0, 2);
                """);

        // really inefficient!
        NotificationManager.getInstance().addListener(newNotifications -> {
            notifications = newNotifications;
            Platform.runLater(() -> {
                contentBox.getChildren().clear();
                for (String n : newNotifications) {
                    HBox h = new HBox();
                    Label label = new Label(n);
                    IconButton button = new IconButton("/icons/check.png");
                    button.setOnAction(e -> {
                        NotificationManager.getInstance().removeNotification(n);
                    });
                    h.getChildren().addAll(label, button);
                    h.setAlignment(Pos.CENTER_RIGHT);
                    contentBox.getChildren().addAll(h);
                }
            });
            if (newNotifications.isEmpty()) {
                popup.hide();
            }
        });

        popup.getContent().add(contentBox);

        bellButton = new IconButton("/icons/bell.png");
        bellButton.setFocusTraversable(false);
        bellButton.setOnAction(_ -> toggle());
        hide();

    }

    public void hide() {
        bellButton.setVisible(false);
    }

    public void show() {
        bellButton.setVisible(true);
    }

    public Button getButton() {
        return bellButton;
    }

    private void toggle() {
        if (popup.isShowing()) {
            popup.hide();
        } else if (!notifications.isEmpty()) {
            popup.show(bellButton, 0, 0);
            Platform.runLater(() -> {
                double buttonRightX = bellButton.localToScreen(bellButton.getWidth(), 0).getX();
                double popupWidth = popup.getWidth();
                double popupX = buttonRightX - popupWidth;
                double popupY = bellButton.localToScreen(0, bellButton.getHeight()).getY();

                popup.setX(popupX);
                popup.setY(popupY);
            });
        }
    }

}
