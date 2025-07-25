package pizzapal.ui.components;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pizzapal.Helper;

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

        setOnMouseEntered(_ -> setCursor(Cursor.HAND));
        setOnMouseExited(_ -> setCursor(Cursor.DEFAULT));
    }

}
