package pizzapal;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pizzapal.ui.mainmenu.MainMenu;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        Scene scene = new Scene(new MainMenu(stage));

        Image appIcon = Helper.loadImage(Config.APP_ICON_PATH);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.setTitle(Config.APP_NAME);
        stage.getIcons().add(appIcon);
        stage.show();
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static void main(String[] args) {
        launch();
    }

}