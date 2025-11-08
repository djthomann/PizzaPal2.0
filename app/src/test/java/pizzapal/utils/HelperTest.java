package pizzapal.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pizzapal.model.domain.core.Storage;

public class HelperTest {

    private Storage storage;

    private static final Double DELTA = 0.0001;

    @BeforeEach
    public void init() {
        storage = new Storage(6f, 3f);
    }

    @Test
    public void testConvertMetersToPixels() {
        assertEquals(Config.PIXEL_PER_METER, Helper.convertMetersToPixel(1f), DELTA);
    }

    @Test
    public void testConvertPixelsToMeters() {

    }

    @Test
    public void testConvertPixelPositionToHeightInStorage() {
        float top = storage.getHeight();
        assertEquals(top, Helper.convertPixelPositionToHeightInStorage(storage, 0), DELTA);

        float bottom = 0;
        assertEquals(bottom, Helper.convertPixelPositionToHeightInStorage(storage,
                Helper.convertMetersToPixel(storage.getHeight())));

        float point1 = 2;
        assertEquals(point1, Helper.convertPixelPositionToHeightInStorage(storage, 150));

        float point2 = 1;
        assertEquals(point2, Helper.convertPixelPositionToHeightInStorage(storage, 300));
    }

}
