package pizzapal.ui.support;

import javafx.scene.Cursor;
import pizzapal.Helper;
import pizzapal.model.Support;

public class SupportController {

    private final Support model;

    private final SupportView view;

    private double dragOffsetX;
    private double dragOffsetY;

    public SupportController(Support support) {
        this.model = support;

        float widthPx = Helper.convertMetersToPixel(model.getWidth());
        float heightPx = Helper.convertMetersToPixel(model.getHeight());
        float posX = Helper.convertMetersToPixel(model.getPositionX());
        float posY = Helper.convertMetersToPixel(model.getPositionY());

        view = new SupportView(widthPx, heightPx, posX, posY);

        initDragAndDrop();

    }

    public SupportView getView() {
        return view;
    }

    public void initDragAndDrop() {

        view.setOnMousePressed(e -> {
            dragOffsetX = e.getSceneX() - view.getLayoutX();
            dragOffsetY = e.getSceneY() - view.getLayoutY();
            view.setCursor(Cursor.MOVE);
        });

        view.setOnMouseEntered(_ -> {
            // view.setCursor(Cursor.HAND);
        });

        view.setOnMouseDragged(e -> {

            view.setCursor(Cursor.MOVE);

            double newX = e.getSceneX() - dragOffsetX;
            double newY = e.getSceneY() - dragOffsetY;

            view.setLayoutX(newX);
            view.setLayoutY(newY);
        });

        view.setOnMouseReleased(e -> {
            view.setCursor(Cursor.HAND);

            dragOffsetX = 0;
            dragOffsetY = 0;
        });

        view.setOnMouseExited(_ -> {
            // view.setCursor(Cursor.DEFAULT);
        });
    }

}
