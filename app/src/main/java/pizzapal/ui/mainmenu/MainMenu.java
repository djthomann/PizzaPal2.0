package pizzapal.ui.mainmenu;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pizzapal.Helper;
import pizzapal.model.repository.StorageRepository;
import pizzapal.ui.UIConfig;
import pizzapal.ui.editor.EditorView;

public class MainMenu extends Pane {

    private StorageRepository repository = StorageRepository.getInstance();

    private Stage stage;

    public MainMenu(Stage stage) {

        this.stage = stage;

        setPrefSize(1000, 700);
        setMinSize(1000, 700);
        setMaxSize(1000, 700);

        Label label = new Label("Pizza Pal 2.0");
        label.setFont(UIConfig.BOLD_FONT);

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setSpacing(20);

        Button newStorageButton = new Button("New Storage");
        newStorageButton.setOnAction(e -> {
            stage.setScene(new Scene(new EditorView(repository.createStorage())));
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        });

        Button openStorageButton = new Button("Open Storage");

        Button exitApplicationButton = new Button("Exit");
        exitApplicationButton.setOnAction(e -> {
            Platform.exit();
        });

        buttonBox.getChildren().addAll(newStorageButton, openStorageButton, exitApplicationButton);

        Image image = Helper.loadImage("/icons/app-icon.png");
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.RIGHT, 100, false, Side.BOTTOM, 100, false),
                new BackgroundSize(0.50, BackgroundSize.AUTO, true, true, false, false));

        this.setBackground(new Background(backgroundImage));

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.BASELINE_LEFT);
        vBox.setPadding(new Insets(20));
        vBox.getChildren().addAll(label, buttonBox);

        this.getChildren().addAll(vBox);
    }

}
