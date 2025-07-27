package pizzapal.ui.view.app.editor;

import javafx.geometry.Orientation;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.control.ToolBar;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class ToolBarView extends ToolBar {

    public ToolBarView() {
        Rectangle supportButton = new Rectangle(20, 100);
        supportButton.setOnDragDetected(e -> {
            Dragboard db = supportButton.startDragAndDrop(TransferMode.COPY);
            db.setDragView(new WritableImage(1, 1));
            ClipboardContent content = new ClipboardContent();
            content.putString("SUPPORT");
            db.setContent(content);
            e.consume();
        });

        Box itemButton = new Box(10, 10, 10);
        itemButton.setOnDragDetected(e -> {
            Dragboard db = itemButton.startDragAndDrop(TransferMode.COPY);
            db.setDragView(new WritableImage(1, 1));
            ClipboardContent content = new ClipboardContent();
            content.putString("ITEM");
            db.setContent(content);
            e.consume();
        });

        Rectangle boardButton = new Rectangle(50, 10);
        boardButton.setOnDragDetected(e -> {
            Dragboard db = boardButton.startDragAndDrop(TransferMode.COPY);
            db.setDragView(new WritableImage(1, 1));
            ClipboardContent content = new ClipboardContent();
            content.putString("BOARD");
            db.setContent(content);
            e.consume();
        });

        Box cube = new Box(100, 100, 100);

        // Material (Farbe)
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.CORNFLOWERBLUE);
        cube.setMaterial(material);

        // Rotation (optional)
        cube.getTransforms().addAll(
                new Rotate(30, Rotate.Y_AXIS),
                new Rotate(20, Rotate.X_AXIS));

        // Licht
        PointLight light = new PointLight(Color.WHITE);
        light.setTranslateX(250);
        light.setTranslateY(-100);
        light.setTranslateZ(-300);

        AmbientLight ambient = new AmbientLight(Color.rgb(80, 80, 80));

        // Kamera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-500);

        // Root und Szene
        Group root = new Group(cube, light, ambient);

        this.setOrientation(Orientation.VERTICAL);
        this.getItems().addAll(supportButton, boardButton, itemButton, root);
    }

}
