package pizzapal.ui.view.app.mainmenu;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.repository.StorageRepository;
import pizzapal.ui.UIConfig;
import pizzapal.ui.components.TextButton;
import pizzapal.ui.view.app.editor.EditorViewController;
import pizzapal.ui.view.app.mainmenu.submenu.NewStorageView;
import pizzapal.ui.view.app.mainmenu.submenu.SettingsView;
import pizzapal.ui.view.app.mainmenu.submenu.SubMenuView;
import pizzapal.utils.Config;
import pizzapal.utils.Helper;
import pizzapal.utils.SceneManager;

public class MainMenu extends StackPane {

    private SettingsView settingsView;

    private NewStorageView newStorageView;

    private VBox mainMenu;

    private ImageView imageView;

    public MainMenu() {

        settingsView = new SettingsView();
        settingsView.getBackButton().setOnAction(e -> {
            removeView(settingsView);
        });

        newStorageView = new NewStorageView();
        newStorageView.getBackButton().setOnAction(e -> {
            removeView(newStorageView);
        });

        setPrefSize(1000, 700);
        setMinSize(1000, 700);
        setMaxSize(1000, 700);

        Label labelTitle = new Label(Config.APP_NAME);
        labelTitle.setFont(UIConfig.EXTRA_LARGE_BOLD_FONT);

        /*
         * Text text = new Text("Hallo Welt");
         * text.setFont(UIConfig.EXTRA_LARGE_BOLD_FONT);
         * 
         * LinearGradient gradient = new LinearGradient(
         * 0, 0, 1, 0, true, CycleMethod.REPEAT,
         * new Stop(0, Color.RED),
         * new Stop(1, Color.YELLOW));
         * text.setFill(gradient);
         * 
         * // Animation: Gradient verschieben
         * Timeline timeline = new Timeline(
         * new KeyFrame(Duration.ZERO, e -> {
         * double t = (System.currentTimeMillis() % 2000) / 2000.0;
         * LinearGradient movingGradient = new LinearGradient(
         * t, 0, t + 1, 0, true, CycleMethod.REPEAT,
         * new Stop(0, Color.RED),
         * new Stop(0.5, Color.YELLOW),
         * new Stop(1, Color.RED));
         * text.setFill(movingGradient);
         * }),
         * new KeyFrame(Duration.millis(40)) // alle 40ms updaten (~25 FPS)
         * );
         * timeline.setCycleCount(Animation.INDEFINITE);
         * timeline.play();
         */

        Label labelDescription = new Label("Your interactive storage solution!");
        labelDescription.setFont(UIConfig.NORMAL_MEDIUM_FONT);

        VBox buttonBox = new VBox();
        buttonBox.setAlignment(Pos.CENTER_LEFT);

        TextButton newStorageButton = new TextButton("New Storage");
        newStorageButton.setOnAction(e -> {
            showSubMenu(newStorageView);
        });

        TextButton openStorageButton = new TextButton("Open Storage");
        openStorageButton.setOnAction(e -> {

            Storage storage = StorageRepository.loadFromFileChooser();

            if (storage != null) {
                SceneManager.getInstance()
                        .showView(new EditorViewController(storage).getView());
            }
        });

        TextButton openSettingsButton = new TextButton("Settings");
        openSettingsButton.setOnAction(e -> {
            showSubMenu(settingsView);
        });

        TextButton exitApplicationButton = new TextButton("Exit");
        exitApplicationButton.setOnAction(e -> {
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
        imageView = new ImageView(image);

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
        this.getChildren().addAll(mainMenu, imageView);

    }

    public void showSubMenu(SubMenuView newView) {

        TranslateTransition moveImage = new TranslateTransition(Duration.millis(200), imageView);
        switch (newView.getPosition()) {
            case TOP_RIGHT -> {
                moveImage.setToX(-500);
            }
            case BOTTOM_LEFT -> {
                moveImage.setToY(-350);
            }
        }
        moveImage.setInterpolator(Interpolator.EASE_BOTH);

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

        moveImage.setOnFinished(e -> {
            this.getChildren().remove(mainMenu);
            this.getChildren().add(0, newView);
            fadeIn.play();
        });

        fadeIn.setOnFinished(e -> {
            newView.onShow();
        });

        fadeOut.setOnFinished(e -> moveImage.play());

        fadeOut.play();
    }

    public void removeView(SubMenuView view) {

        TranslateTransition moveImage = new TranslateTransition(Duration.millis(200), imageView);
        moveImage.setToX(500);
        moveImage.setToY(350);
        moveImage.setInterpolator(Interpolator.EASE_BOTH);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), view);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), mainMenu);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        moveImage.setOnFinished(e -> {
            this.getChildren().remove(view);
            this.getChildren().add(0, mainMenu);
            fadeIn.play();
        });

        fadeOut.setOnFinished(e -> moveImage.play());

        fadeOut.play();
    }

}
