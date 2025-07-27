package pizzapal.ui.view.app.editor;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ToolBar;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ToolBarView extends ToolBar {

    public ToolBarView() {
        Rectangle supportButton = new Rectangle(15, 50);
        supportButton.setOnDragDetected(e -> {
            Dragboard db = supportButton.startDragAndDrop(TransferMode.COPY);
            db.setDragView(new WritableImage(1, 1));
            ClipboardContent content = new ClipboardContent();
            content.putString("SUPPORT");
            db.setContent(content);
            e.consume();
        });

        Rectangle itemButton = new Rectangle(25, 25);
        itemButton.setOnDragDetected(e -> {
            Dragboard db = itemButton.startDragAndDrop(TransferMode.COPY);
            db.setDragView(new WritableImage(1, 1));
            ClipboardContent content = new ClipboardContent();
            content.putString("ITEM");
            db.setContent(content);
            e.consume();
        });

        Rectangle boardButton = new Rectangle(50, 15);
        boardButton.setOnDragDetected(e -> {
            Dragboard db = boardButton.startDragAndDrop(TransferMode.COPY);
            db.setDragView(new WritableImage(1, 1));
            ClipboardContent content = new ClipboardContent();
            content.putString("BOARD");
            db.setContent(content);
            e.consume();
        });

        this.setOrientation(Orientation.VERTICAL);

        VBox tools = new VBox(supportButton, boardButton, itemButton);
        tools.setSpacing(10);
        tools.setAlignment(Pos.CENTER);
        tools.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(tools, Priority.ALWAYS);

        this.getItems().addAll(tools);
    }

}
