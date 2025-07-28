package pizzapal.ui.view.app.mainmenu;

import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import pizzapal.model.repository.StorageRepository;
import pizzapal.ui.UIConfig;
import pizzapal.ui.components.TextButton;
import pizzapal.ui.view.app.editor.EditorView;
import pizzapal.utils.Helper;
import pizzapal.utils.SceneManager;

public class MainMenu extends StackPane {

    private StorageRepository repository = StorageRepository.getInstance();

    public MainMenu() {

        setPrefSize(1000, 700);
        setMinSize(1000, 700);
        setMaxSize(1000, 700);

        Label labelTitle = new Label("Pizza Pal 2.0");
        labelTitle.setFont(UIConfig.LARGE_BOLD_FONT);

        Label labelDescription = new Label("Your interactive storage solution!");
        labelDescription.setFont(UIConfig.NORMAL_MEDIUM_FONT);

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setSpacing(20);

        TextButton newStorageButton = new TextButton("New Storage");
        newStorageButton.setOnAction(_ -> {
            SceneManager.getInstance().showView(new EditorView(repository.createStorage()));
        });

        TextButton openStorageButton = new TextButton("Open Storage");

        TextButton openSettingsButton = new TextButton("Settings");

        TextButton exitApplicationButton = new TextButton("Exit");
        exitApplicationButton.setOnAction(_ -> {
            Platform.exit();
        });

        buttonBox.getChildren().addAll(newStorageButton, openStorageButton, openSettingsButton, exitApplicationButton);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.BASELINE_LEFT);
        vBox.setPadding(new Insets(20));
        vBox.getChildren().addAll(labelTitle, labelDescription, buttonBox);

        Image image = Helper.loadImage("/icons/menu-icon.png");
        ImageView imageView = new ImageView(image);

        imageView.setTranslateX(500);
        imageView.setTranslateY(200);

        imageView.setFitWidth(700);
        imageView.setPreserveRatio(true);

        RotateTransition rotate = new RotateTransition(Duration.seconds(45), imageView);
        rotate.setByAngle(360);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setInterpolator(javafx.animation.Interpolator.LINEAR);
        rotate.play();

        this.setBackground(UIConfig.APP_BACKGROUND);
        this.getChildren().addAll(imageView, vBox);

    }

}
