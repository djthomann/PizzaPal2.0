package pizzapal;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import pizzapal.utils.Config;
import pizzapal.utils.Helper;
import pizzapal.utils.SceneManager;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        SceneManager manager = SceneManager.getInstance();
        manager.setStage(stage);

        manager.showMainMenu();

        Image appIcon = Helper.loadImage(Config.APP_ICON_PATH);

        stage.sizeToScene();
        stage.setResizable(false);
        stage.setTitle(Config.APP_NAME);
        stage.getIcons().add(appIcon);
        stage.show();

        manager.centerStage();

    }

    public static void main(String[] args) {
        launch();
    }

}