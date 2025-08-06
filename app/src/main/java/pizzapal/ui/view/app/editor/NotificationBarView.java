package pizzapal.ui.view.app.editor;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import pizzapal.ui.components.NotificationDropdown;
import pizzapal.utils.NotificationManager;

public class NotificationBarView extends HBox {

    public NotificationBarView() {

        NotificationDropdown dropdown = new NotificationDropdown();

        NotificationManager.getInstance().addListener(newNotifications -> {
            Platform.runLater(() -> {
                if (newNotifications.isEmpty()) {
                    dropdown.hide();
                } else {
                    dropdown.show();
                }
            });
        });

        this.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().addAll(dropdown.getBellButton());
    }

}
