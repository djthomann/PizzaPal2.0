package pizzapal.ui;

import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class UIConfig {

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

}
