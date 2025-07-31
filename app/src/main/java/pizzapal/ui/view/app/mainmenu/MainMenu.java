package pizzapal.ui.view.app.mainmenu;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import pizzapal.model.repository.StorageRepository;
import pizzapal.ui.UIConfig;
import pizzapal.ui.components.TextButton;
import pizzapal.ui.view.app.editor.EditorViewController;
import pizzapal.ui.view.app.mainmenu.settings.SettingsView;
import pizzapal.utils.Config;
import pizzapal.utils.Helper;
import pizzapal.utils.SceneManager;

public class MainMenu extends StackPane {

    private StorageRepository repository = StorageRepository.getInstance();

    private SettingsView settingsView;

    private VBox mainMenu;

    private VBox subMenu;

    public MainMenu() {

        settingsView = new SettingsView();
        settingsView.getBackButton().setOnAction(e -> {
            removeView(settingsView);
        });

        setPrefSize(1000, 700);
        setMinSize(1000, 700);
        setMaxSize(1000, 700);

        Label labelTitle = new Label(Config.APP_NAME);
        labelTitle.setFont(UIConfig.EXTRA_LARGE_BOLD_FONT);

        Label labelDescription = new Label("Your interactive storage solution!");
        labelDescription.setFont(UIConfig.NORMAL_MEDIUM_FONT);

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        TextButton newStorageButton = new TextButton("New Storage");
        newStorageButton.setOnAction(_ -> {
            SceneManager.getInstance().showView(new EditorViewController(repository.createStorage()).getView());
        });

        TextButton openStorageButton = new TextButton("Open Storage");

        TextButton openSettingsButton = new TextButton("Settings");
        openSettingsButton.setOnAction(_ -> {
            showSubMenu(settingsView);
        });

        TextButton exitApplicationButton = new TextButton("Exit");
        exitApplicationButton.setOnAction(_ -> {
            Platform.exit();
        });

        buttonBox.getChildren().addAll(newStorageButton, openStorageButton, openSettingsButton, exitApplicationButton);
        buttonBox.setSpacing(20);

        mainMenu = new VBox();
        mainMenu.setAlignment(Pos.BOTTOM_LEFT);
        mainMenu.setPadding(new Insets(60));
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        mainMenu.getChildren().addAll(labelTitle, labelDescription, spacer, buttonBox);

        Image image = Helper.loadImage("/icons/pizza.png");
        ImageView imageView = new ImageView(image);

        imageView.setTranslateX(500);
        imageView.setTranslateY(350);

        imageView.setFitWidth(1000);
        imageView.setPreserveRatio(true);

        RotateTransition rotate = new RotateTransition(Duration.seconds(45), imageView);
        rotate.setByAngle(360);
        rotate.setCycleCount(RotateTransition.INDEFINITE);
        rotate.setInterpolator(javafx.animation.Interpolator.LINEAR);
        rotate.play();

        this.setBackground(UIConfig.APP_BACKGROUND);
        this.getChildren().addAll(imageView, mainMenu);

    }

    public void showSubMenu(Node newView) {
        if (this.getChildren().isEmpty()) {
            this.getChildren().add(newView);
            return;
        }

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), mainMenu);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newView);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        fadeOut.setOnFinished(e -> {
            this.getChildren().remove(mainMenu);
            this.getChildren().add(newView);
            fadeIn.play();
        });

        fadeOut.play();
    }

    public void removeView(Node view) {

        Node oldView = this.getChildren().get(1);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), view);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), mainMenu);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        fadeOut.setOnFinished(e -> {
            this.getChildren().remove(view);
            this.getChildren().add(mainMenu);
            fadeIn.play();
        });

        fadeOut.play();
    }

}
