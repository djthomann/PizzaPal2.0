package pizzapal.ui.components;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pizzapal.utils.Helper;

public class IconButton extends Button {

    private Image image;

    private ImageView imageView;

    public static final int STANDARD_HEIGHT = 32;

    public IconButton(String path) {
        super();

        image = Helper.loadImage(path);
        imageView = new ImageView(image);

        imageView.setFitHeight(24);
        imageView.setFitWidth(24);

        setPrefSize(STANDARD_HEIGHT, STANDARD_HEIGHT);
        setMinSize(STANDARD_HEIGHT, STANDARD_HEIGHT);
        setMaxSize(STANDARD_HEIGHT, STANDARD_HEIGHT);
        setGraphic(imageView);

        setBackground(null);

        setOnMouseEntered(e -> setCursor(Cursor.HAND));
        setOnMouseExited(e -> setCursor(Cursor.DEFAULT));
    }

    public void setImageSize(float width, float height) {
        imageView.setFitHeight(width);
        imageView.setFitWidth(height);
    }

}
