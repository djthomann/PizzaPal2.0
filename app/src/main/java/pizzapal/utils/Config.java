package pizzapal.utils;

import java.util.HashMap;
import java.util.Map;

import javafx.scene.paint.Color;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Entity;
import pizzapal.model.domain.entities.FixedWidthEntity;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;

public class Config {

    public static final float STANDARD_SUPPORT_WIDTH = 0.25f;
    public static final float STANDARD_SUPPORT_HEIGHT = 2f;
    public static final Color STANDARD_SUPPORT_COLOR = Color.BROWN;

    public static final float STANDARD_BOARD_HEIGHT = 0.15f;
    public static final Color STANDARD_BOARD_COLOR = Color.BURLYWOOD;

    public static final float STANDARD_ITEM_WIDTH = 0.4f;
    public static final float STANDARD_ITEM_HEIGHT = 0.4f;
    public static final float STANDARD_ITEM_WEIGHT = 0.5f;
    public static final Color STANDARD_ITEM_COLOR = Color.RED;

    public static Map<EntityField, Float> defaultSizeMap = initialiseSizeMap();

    public static Map<EntityField, Color> defaultColorMap = initialiseColorMap();

    public static final String APP_NAME = "PizzaPal 2.0";

    public static final String APP_ICON_PATH = "/icons/app-icon.png";

    public static int PIXEL_PER_METER = 100;

    public static final float INITIAL_VOLUME = 0;

    private static Map<EntityField, Color> initialiseColorMap() {
        defaultColorMap = new HashMap<>();

        try {
            defaultColorMap.put(new EntityField(Support.class, Entity.class.getDeclaredField("color")),
                    STANDARD_SUPPORT_COLOR);

            defaultColorMap.put(new EntityField(Board.class, Entity.class.getDeclaredField("color")),
                    STANDARD_BOARD_COLOR);

            defaultColorMap.put(new EntityField(Item.class, Entity.class.getDeclaredField("color")),
                    STANDARD_ITEM_COLOR);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        return defaultColorMap;
    }

    private static Map<EntityField, Float> initialiseSizeMap() {
        defaultSizeMap = new HashMap<>();

        try {
            defaultSizeMap.put(new EntityField(Support.class, FixedWidthEntity.class.getDeclaredField("width")),
                    STANDARD_SUPPORT_WIDTH);
            defaultSizeMap.put(new EntityField(Support.class, Entity.class.getDeclaredField("height")),
                    STANDARD_SUPPORT_HEIGHT);

            defaultSizeMap.put(new EntityField(Board.class, Entity.class.getDeclaredField("height")),
                    STANDARD_BOARD_HEIGHT);

            defaultSizeMap.put(new EntityField(Item.class, FixedWidthEntity.class.getDeclaredField("width")),
                    STANDARD_ITEM_WIDTH);
            defaultSizeMap.put(new EntityField(Item.class, Entity.class.getDeclaredField("height")),
                    STANDARD_ITEM_HEIGHT);
            defaultSizeMap.put(new EntityField(Item.class, Item.class.getDeclaredField("weight")),
                    STANDARD_ITEM_WEIGHT);
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

        return defaultSizeMap;
    }

    public static Float getDefaultSize(EntityField field) {
        return defaultSizeMap.get(field);
    }

    public static Color getDefaultColor(EntityField field) {
        return defaultColorMap.get(field);
    }

}
