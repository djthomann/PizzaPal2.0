package pizzapal.ui.view.entities.board;

import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import pizzapal.Helper;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Board;
import pizzapal.ui.view.entities.ViewController;

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
        float posY = Helper.getPixelPositionYInStorage(storageController.getStorage(), board.getPosY());

        this.view = new BoardView(widthPx, heightPx, posX, posY);

        initDragAndDrop();

        board.addListener((model, type) -> {
            switch (type) {
                case MOVE -> {
                    view.updateFromModel(model);
                }
                case DELETE -> {
                    Parent parent = view.getParent();
                    if (parent instanceof Pane pane) {
                        pane.getChildren().remove(view);
                    }
                }
            }
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

                    if (!storageController.moveBoard(board, Helper.convertPixelToMeters((float) e.getSceneX()),
                            Helper.convertPixelToMeters((float) e.getSceneY()))) {
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
