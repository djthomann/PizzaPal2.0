package pizzapal.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UIConfig {

        public static final Font EXTRA_LARGE_BOLD_FONT = Font.font("Arial", FontWeight.BOLD, 86);

        public static final Font LARGE_BOLD_FONT = Font.font("Arial", FontWeight.BOLD, 60);

        public static final Font LARGE_MEDIUM_FONT = Font.font("Arial", FontWeight.MEDIUM, 60);

        public static final Font NORMAL_MEDIUM_FONT = Font.font("Arial", FontWeight.MEDIUM, 32);

        public static final Font SMALL_NORMAL_BOLD_FONT = Font.font("Arial", FontWeight.BOLD, 18);

        public static final Font SMALL_BOLD_FONT = Font.font("Arial", FontWeight.BOLD, 12);

        public static final Background APP_BACKGROUND = new Background(
                        new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));

        public static final Background APP_BACKGROUND_DARKER = new Background(
                        new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY));

        public static final Color STORAGE_BACKGROUND_COLOR = Color.LIGHTGRAY.darker();

        public static final Background STORAGE_BACKGROUND = new Background(
                        new BackgroundFill(STORAGE_BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY));

        public static final Background STORAGE_BACKGROUND_DARK = new Background(
                        new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY));

        public static final Background SUPPORT_BACKGROUND = new Background(
                        new BackgroundFill(Color.BROWN, CornerRadii.EMPTY, Insets.EMPTY));

        public static final Background SUPPORT_GHOST_BACKGROUND = STORAGE_BACKGROUND_DARK;

        public static final Background BOARD_BACKGROUND = new Background(
                        new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY));

        public static final int CONTEXT_MENU_OFFSET_PIXEL = 5;

        public static final Color HOVER_COLOR = Color.ORANGE;

        public static final Color BUTTON_COLOR = Color.LIGHTGRAY;
        public static final CornerRadii BUTTON_CORNER_RADII = new CornerRadii(5);

        public static final Background BUTTON_NORMAL_BACKGROUND = new Background(
                        new BackgroundFill(BUTTON_COLOR, new CornerRadii(5), Insets.EMPTY));

        public static final Background BUTTON_GRADIENT_BACKGROUND = new Background(new BackgroundFill(
                        new LinearGradient(
                                        0, 0, 1, 0,
                                        true,
                                        CycleMethod.NO_CYCLE,
                                        new Stop(0, Color.ORANGERED),
                                        new Stop(1, Color.ORANGE)),
                        BUTTON_CORNER_RADII,
                        Insets.EMPTY));

        public static final Border BUTTON_NORMAL_BORDER = new Border(new BorderStroke(
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        CornerRadii.EMPTY,
                        new BorderWidths(1)));

        public static final Background BUTTON_HOVER = new Background(
                        new BackgroundFill(HOVER_COLOR, BUTTON_CORNER_RADII, Insets.EMPTY));

}
