package pizzapal;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pizzapal.model.entities.Board;
import pizzapal.model.entities.Support;
import pizzapal.model.storage.Storage;
import pizzapal.model.storage.StorageController;
import pizzapal.ui.storage.StorageView;
import pizzapal.ui.toolbar.ToolbarView;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {

        float widthInMeters = 9.0f;
        float heightInMeters = 5f;

        Storage storage = new Storage(widthInMeters, heightInMeters);
        Support support1 = new Support(storage, 0.2f, 2f, 1f, 0f);
        Support support2 = new Support(storage, 0.2f, 2f, 2f, 0f);

        Board board1 = new Board(support1, support2, 0.3f, 0.2f);

        storage.addSupport(support1);
        storage.addSupport(support2);

        storage.addBoard(board1);

        StorageController storageController = new StorageController(storage);

        StorageView storageView = new StorageView(storageController);

        ToolbarView toolbar = new ToolbarView();

        BorderPane pane = new BorderPane();

        pane.setCenter(storageView);
        // pane.setLeft(toolbar);

        Scene scene = new Scene(pane);

        Image appIcon = Helper.loadImage(Config.APP_ICON_PATH);

        stage.setScene(scene);
        stage.sizeToScene();
        stage.setResizable(false);
        stage.setTitle(Config.APP_NAME);
        stage.getIcons().add(appIcon);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}