package pizzapal;

import javafx.scene.image.Image;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Support;

public class Helper {

    public static final float convertMetersToPixel(float meters) {
        return meters * Config.PIXEL_PER_METER;
    }

    public static final float convertPixelToMeters(float pixels) {
        return (float) (pixels / Config.PIXEL_PER_METER);
    }

    public static float getPixelPositionYInStorage(Storage storage, Support support) {
        return convertMetersToPixel(storage.getHeight()) - convertMetersToPixel(support.getHeight());
    }

    public static float getPixelPositionYInStorage(Storage storage, float posY) {
        return convertMetersToPixel(storage.getHeight()) - convertMetersToPixel(posY);
    }

    public static Image loadImage(String resourcePath) {
        try {
            var url = Helper.class.getResource(resourcePath);
            if (url == null) {
                System.err.println("Resource nicht gefunden: " + resourcePath);
                return null;
            }
            return new Image(url.toExternalForm());
        } catch (Exception e) {
            System.err.println("Fehler beim Laden des Bildes: " + resourcePath);
            e.printStackTrace();
            return null;
        }
    }

}
