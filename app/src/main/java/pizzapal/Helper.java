package pizzapal;

public class Helper {
    
    public static final int convertMetersToPixel(float meters) {
        return (int)(meters * Config.PIXEL_PER_METER);
    }

}
