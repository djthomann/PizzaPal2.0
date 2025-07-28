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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UIConfig {

        public static final Font LARGE_BOLD_FONT = Font.font("Arial", FontWeight.BOLD, 60);

        public static final Font LARGE_MEDIUM_FONT = Font.font("Arial", FontWeight.MEDIUM, 60);

        public static final Font NORMAL_MEDIUM_FONT = Font.font("Arial", FontWeight.MEDIUM, 32);

        public static final Background APP_BACKGROUND = new Background(
                        new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));

        public static final Background STORAGE_BACKGROUND = new Background(
                        new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY));

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

        public static final Background BUTTON_NORMAL_BACKGROUND = new Background(
                        new BackgroundFill(BUTTON_COLOR, new CornerRadii(5), Insets.EMPTY));

        public static final Border BUTTON_NORMAL_BORDER = new Border(new BorderStroke(
                        Color.BLACK,
                        BorderStrokeStyle.SOLID,
                        new CornerRadii(5),
                        new BorderWidths(1)));

        public static final Background BUTTON_HOVER = new Background(
                        new BackgroundFill(HOVER_COLOR, new CornerRadii(5), Insets.EMPTY));

}
