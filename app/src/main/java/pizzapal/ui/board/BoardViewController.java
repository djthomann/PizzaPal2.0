package pizzapal.ui.board;

import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import pizzapal.Helper;
import pizzapal.model.entities.Board;
import pizzapal.model.storage.StorageController;
import pizzapal.ui.ViewController;

public class BoardViewController implements ViewController {

    private Board board;

    private StorageController storageController;

    private BoardView view;

    public BoardViewController(StorageController storageController, Board board) {

        this.board = board;

        this.storageController = storageController;

        float widthPx = Helper.convertMetersToPixel(board.getWidth());
        float heightPx = Helper.convertMetersToPixel(0.2f);
        float posX = Helper
                .convertMetersToPixel(board.getSupportLeft().getPositionX() + board.getSupportLeft().getWidth());
        float posY = Helper.convertMetersToPixel(board.getPosY());

        this.view = new BoardView(widthPx, heightPx, posX, posY);

        initDragAndDrop();

        board.addListener(model -> {
            view.updateFromModel(model);
        });

    }

    @Override
    public BoardView getView() {
        return view;
    }

    public void initDragAndDrop() {

        view.setOnMousePressed(e -> {

            MouseButton button = e.getButton();

            switch (button) {
                case PRIMARY -> {
                    view.setCursor(Cursor.MOVE);
                }
                case SECONDARY -> {
                    // view.toggleContextMenu();
                }
                default -> {
                }
            }

        });

        view.setOnMouseEntered(_ -> {
            // view.setCursor(Cursor.HAND);
        });

        view.setOnMouseDragged(e -> {

            MouseButton button = e.getButton();

            switch (button) {
                case PRIMARY -> {
                    view.setCursor(Cursor.MOVE);
                    view.moveRectangle((float) e.getX(), (float) e.getY());
                }
                default -> {
                }
            }

        });

        view.setOnMouseReleased(e -> {

            MouseButton button = e.getButton();

            switch (button) {
                case PRIMARY -> {

                    if (!storageController.moveBoard(board, (float) e.getSceneX(), (float) e.getSceneY())) {
                        view.resetRectangle();
                    }
                }
                default -> {
                }
            }

            view.setCursor(Cursor.HAND);

        });

        view.setOnMouseExited(_ -> {
            // view.setCursor(Cursor.DEFAULT);
        });
    }

}
