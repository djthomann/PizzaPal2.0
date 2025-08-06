package pizzapal.ui.view.app.editor;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import pizzapal.ui.components.NotificationDropdown;
import pizzapal.utils.NotificationManager;

public class NotificationBarView extends HBox {

    public NotificationBarView() {
        MenuBar notificationCenter = new MenuBar();
        Menu notificationMenu = new Menu("Notifications");
        MenuItem notificationItem = new MenuItem("Notfications");
        notificationMenu.getItems().add(notificationItem);
        notificationCenter.getMenus().add(notificationMenu);

        NotificationDropdown dropdown = new NotificationDropdown();

        NotificationManager.getInstance().addListener(newNotifications -> {
            Platform.runLater(() -> {
                if (newNotifications.isEmpty()) {
                    dropdown.hide();
                } else {
                    dropdown.show();
                }
                notificationMenu.getItems().clear();
                for (String n : newNotifications) {
                    notificationMenu.getItems().add(new MenuItem(n));
                }
            });
        });

        this.setAlignment(Pos.CENTER_RIGHT);
        this.getChildren().add(dropdown.getButton());
    }

}
