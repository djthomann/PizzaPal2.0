package pizzapal.ui.view.app.editor;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Duration;
import pizzapal.ui.components.NotificationDropdown;
import pizzapal.utils.NotificationManager;

public class NotificationBarView extends HBox {

    private PauseTransition currentPause;
    private FadeTransition currentFade;

    public NotificationBarView() {

        NotificationDropdown dropdown = new NotificationDropdown();

        NotificationManager.getInstance().addNewListener(newNotifications -> {
            Platform.runLater(() -> {
                if (newNotifications.isEmpty()) {
                    dropdown.hide();
                } else {
                    dropdown.show();
                }
            });
        });

        Text text = new Text();

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        this.setAlignment(Pos.CENTER_RIGHT);
        this.setPadding(new Insets(0, 10, 0, 10));
        this.getChildren().addAll(text, spacer, dropdown.getBellButton());

        NotificationManager.getInstance().addNewListener(newNotification -> {

            if (currentPause != null)
                currentPause.stop();
            if (currentFade != null)
                currentFade.stop();

            text.setText(newNotification);
            text.setOpacity(1);

            currentPause = new PauseTransition(Duration.seconds(5));
            currentPause.setOnFinished(event -> {
                currentFade = new FadeTransition(Duration.seconds(1), text);
                currentFade.setFromValue(1.0);
                currentFade.setToValue(0.0);
                currentFade.setOnFinished(e -> text.setText(""));
                currentFade.play();
            });
            currentPause.play();
        });

    }

}
