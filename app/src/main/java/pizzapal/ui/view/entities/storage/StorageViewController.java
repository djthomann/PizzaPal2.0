package pizzapal.ui.view.entities.storage;

import java.util.List;

import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pizzapal.Helper;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.view.entities.board.BoardViewController;
import pizzapal.ui.view.entities.item.ItemViewController;
import pizzapal.ui.view.entities.support.SupportView;
import pizzapal.ui.view.entities.support.SupportViewController;

public class StorageViewController {

    private StorageView storageView;

    private StorageController storageController;

    public StorageViewController(StorageController storageController) {

        this.storageController = storageController;
        Storage storage = storageController.getStorage();

        int widthPx = Helper.convertMetersToPixel(storage.getWidth());
        int heightPx = Helper.convertMetersToPixel(storage.getHeight());

        storageView = new StorageView(widthPx, heightPx);

        List<Support> supports = storage.getSupports();

        for (Support support : supports) {
            storageView.getChildren().add(new SupportViewController(storageController, support).getView());
        }

        List<Board> boards = storage.getBoards();

        for (Board board : boards) {
            storageView.getChildren().add(new BoardViewController(storageController, board).getView());

            for (Item item : board.getItems()) {
                storageView.getChildren().add(new ItemViewController(item).getView());
            }
        }

        initDragAndDrop();
    }

    public void initDragAndDrop() {

        storageView.setOnDragOver(e -> {
            if (e.getGestureSource() != storageView && e.getDragboard().hasString()) {
                e.acceptTransferModes(TransferMode.COPY);
                storageView.showGhostRectangle();
                storageView.moveGhostRectangle((float) e.getX(), (float) e.getY());
            }
            e.consume();
        });

        storageView.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasString() && db.getString().equals("SUPPORT")) {
                SupportView supportView = new SupportViewController(storageController,
                        new Support(storageController.getStorage(), 0.3f, 1f, 4f, 0)).getView();
                storageView.getChildren().add(supportView);
                storageView.hideGhostRectangle();
                e.setDropCompleted(true);
            } else if (db.hasString() && db.getString().equals("ITEM")) {

                Rectangle newItem = new Rectangle(20, 20);
                newItem.setFill(Color.YELLOW);
                newItem.setLayoutX(e.getX());
                newItem.setLayoutY(e.getY());
                storageView.getChildren().add(newItem);
                storageView.hideGhostRectangle();
                e.setDropCompleted(true);
            } else {
                e.setDropCompleted(false);
            }
            e.consume();
        });

    }

    public StorageView getView() {
        return storageView;
    }

}
