package pizzapal.ui.view.entities.storage;

import java.util.List;

import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;
import pizzapal.ui.view.entities.board.BoardViewController;
import pizzapal.ui.view.entities.item.ItemViewController;
import pizzapal.ui.view.entities.support.SupportViewController;
import pizzapal.utils.Helper;
import pizzapal.utils.ToolState;
import pizzapal.utils.ToolState.Tool;

public class StorageViewController {

    private StorageView storageView;

    private StorageController storageController;

    private ToolState toolState;

    public StorageViewController(StorageController storageController) {
        this(storageController, new ToolState());
    }

    public StorageViewController(StorageController storageController, ToolState toolState) {

        this.storageController = storageController;
        this.toolState = toolState;

        storageController.addSupportCreationListener(support -> {
            storageView.getChildren().add(new SupportViewController(storageController, toolState, support).getView());
        });

        storageController.addBoardCreationListener(board -> {
            storageView.getChildren().add(new BoardViewController(storageController, toolState, board).getView());
        });

        storageController.addItemCreationListener(item -> {
            storageView.getChildren().addAll(new ItemViewController(storageController, toolState, item).getView());
        });

        Storage storage = storageController.getStorage();

        float widthPx = Helper.convertMetersToPixel(storage.getWidth());
        float heightPx = Helper.convertMetersToPixel(storage.getHeight());

        storageView = new StorageView(widthPx, heightPx);

        List<Support> supports = storage.getSupports();

        for (Support support : supports) {
            storageView.getChildren().add(new SupportViewController(storageController, toolState, support).getView());
        }

        List<Board> boards = storage.getBoards();

        for (Board board : boards) {
            storageView.getChildren().add(new BoardViewController(storageController, toolState, board).getView());

            for (Item item : board.getItems()) {
                storageView.getChildren().add(new ItemViewController(storageController, toolState, item).getView());
            }
        }

        init();
    }

    public void init() {

        storageView.setOnMouseExited(e -> {
            storageView.hideGhostRectangle();
        });

        storageView.setOnMouseClicked(e -> {

            Tool selectedTool = toolState.getCurrentTool();

            float posX = Helper.convertPixelToMeters((float) e.getX());
            float posY = Helper.convertPixelToMeters((float) e.getY());

            switch (selectedTool) {
                case SELECT -> {
                    // Do nothing
                }
                case PICKCOLOR -> {

                }
                case BOARD -> {
                    storageController.addBoard(toolState.getBoardHeight(), toolState.getBoardColor(), posX, Helper
                            .convertPixelPositionToHeightInStorage(storageController.getStorage(), (float) e.getY()));
                    storageView.hideGhostRectangle();
                }
                case SUPPORT -> {
                    storageController.addSupport(toolState.getSupportWidth(), toolState.getSupportHeight(),
                            toolState.getSupportColor(), posX, posY);
                    storageView.hideGhostRectangle();
                }
                case ITEM -> {
                    storageController.addItem(toolState.getItemWidth(), toolState.getItemHeight(), posX, posY);
                    storageView.hideGhostRectangle();
                }
            }
        });

        storageView.setOnMouseMoved(e -> {

            Tool selectedTool = toolState.getCurrentTool();

            switch (selectedTool) {
                case SELECT -> {
                    // Do nothing
                }
                case BOARD -> {
                    storageView.setGhostRectangleSize(Helper.convertMetersToPixel(0.5f),
                            Helper.convertMetersToPixel(toolState.getBoardHeight()));
                    storageView.showGhostRectangle();
                    storageView.moveGhostRectangle((float) e.getX(), (float) e.getY());
                }
                case SUPPORT -> {
                    storageView.setGhostRectangleSize(Helper.convertMetersToPixel(toolState.getSupportWidth()),
                            Helper.convertMetersToPixel(toolState.getSupportHeight()));
                    storageView.showGhostRectangle();
                    storageView.moveGhostRectangle((float) e.getX(), (float) e.getY());
                }
                case ITEM -> {
                    storageView.setGhostRectangleSize(Helper.convertMetersToPixel(toolState.getItemWidth()),
                            Helper.convertMetersToPixel(toolState.getItemHeight()));
                    storageView.showGhostRectangle();
                    storageView.moveGhostRectangle((float) e.getX(), (float) e.getY());
                }
            }

        });

    }

    public StorageView getView() {
        return storageView;
    }

}
