package pizzapal.ui.view.entities.storage;

import java.util.List;

import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.view.entities.board.BoardViewController;
import pizzapal.ui.view.entities.item.ItemViewController;
import pizzapal.ui.view.entities.support.SupportViewController;
import pizzapal.utils.Helper;

public class StorageViewController {

    private StorageView storageView;

    private StorageController storageController;

    public StorageViewController(StorageController storageController) {

        this.storageController = storageController;

        storageController.addSupportCreationListener(support -> {
            storageView.getChildren().add(new SupportViewController(storageController, support).getView());
        });

        storageController.addBoardCreationListener(board -> {
            storageView.getChildren().add(new BoardViewController(storageController, board).getView());
        });

        storageController.addItemCreationListener(item -> {
            storageView.getChildren().addAll(new ItemViewController(item).getView());
        });

        Storage storage = storageController.getStorage();

        float widthPx = Helper.convertMetersToPixel(storage.getWidth());
        float heightPx = Helper.convertMetersToPixel(storage.getHeight());

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
                storageController.addSupport(Helper.convertPixelToMeters((float) e.getX()),
                        Helper.convertPixelToMeters((float) e.getY()));
                storageView.hideGhostRectangle();
                e.setDropCompleted(true);
            } else if (db.hasString() && db.getString().equals("ITEM")) {
                storageController.addItem(Helper.convertPixelToMeters((float) e.getX()),
                        Helper.convertPixelToMeters((float) e.getY()));
                storageView.hideGhostRectangle();
                e.setDropCompleted(true);
            } else if (db.hasString() && db.getString().equals("BOARD")) {
                storageController.addBoard(Helper.convertPixelToMeters((float) e.getX()),
                        Helper.convertPixelToMeters((float) e.getY()));
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
