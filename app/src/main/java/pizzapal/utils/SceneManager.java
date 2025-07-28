package pizzapal.utils;

import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pizzapal.ui.view.app.mainmenu.MainMenu;

public class SceneManager {

    private static SceneManager instance;
    private Stage stage;

    private SceneManager() {

    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void sizeStage() {
        stage.sizeToScene();
        centerStage();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void showMainMenu() {
        showView(new MainMenu());
    }

    public void showView(Parent view) {

        Scene scene = new Scene(view);
        stage.setScene(scene);
        centerStage();

    }

    public void centerStage() {
        Rectangle2D screen = Screen.getPrimary().getVisualBounds();
        stage.setX((screen.getWidth() - stage.getWidth()) / 2);
        stage.setY((screen.getHeight() - stage.getHeight()) / 2);
    }

}
