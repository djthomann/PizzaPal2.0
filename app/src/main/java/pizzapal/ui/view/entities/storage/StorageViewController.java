package pizzapal.ui.view.entities.storage;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Entity;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.SerializableColor;
import pizzapal.model.domain.entities.Support;
import pizzapal.model.observability.ObservableField;
import pizzapal.model.reflection.ReflectionUtils;
import pizzapal.ui.view.entities.board.BoardViewController;
import pizzapal.ui.view.entities.item.ItemViewController;
import pizzapal.ui.view.entities.support.SupportViewController;
import pizzapal.utils.EntityField;
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

        storageView = new StorageView(storage.getColor().getColor(), widthPx, heightPx);

        List<Support> supports = storage.getSupports();

        for (Support support : supports) {
            storageView.getChildren().add(new SupportViewController(storageController, toolState, support).getView());

            List<Board> boards = support.getBoardsLeft();

            for (Board board : boards) {
                storageView.getChildren().add(new BoardViewController(storageController, toolState, board).getView());

                for (Item item : board.getItems()) {
                    storageView.getChildren().add(new ItemViewController(storageController, toolState, item).getView());
                }
            }
        }

        init();
    }

    public void changeGhostRectangle() {
        float height = Helper.convertMetersToPixel(0);
        float width = Helper.convertMetersToPixel(0);
        Class<? extends Entity> clazz = toolState.getSelectedEntityClass();
        if (clazz == null) {
            storageView.setGhostRectangleSize(width, height);
        } else {
            try {
                height = Helper.convertMetersToPixel(
                        (Float) toolState.getValue(
                                new EntityField(toolState.getSelectedEntityClass(),
                                        Entity.class.getDeclaredField("height"))));
            } catch (NoSuchFieldException | SecurityException e1) {
                height = 0.5f;
                e1.printStackTrace();
            }

            width = Helper.convertMetersToPixel(1f);
            try {
                Object value = toolState.getValue(
                        new EntityField(toolState.getSelectedEntityClass(),
                                ReflectionUtils.getFieldFromSuperClass(toolState.getSelectedEntityClass(),
                                        "width")));
                if (value != null) {
                    width = Helper.convertMetersToPixel(
                            (Float) value);
                }

            } catch (SecurityException e1) {
                height = 0.5f;
                e1.printStackTrace();
            }
        }

        storageView.setGhostRectangleSize(width, height);
    }

    public void init() {

        toolState.toolObservable().addListener((obs, oldValue, newValue) -> {
            changeGhostRectangle();
        });

        toolState.mapChangeObservable().addListener((obs, oldValue, newValue) -> {
            changeGhostRectangle();
        });

        storageView.setOnMouseExited(e -> {
            storageView.hideGhostRectangle();
        });

        storageView.setOnMouseClicked(e -> {

            Tool selectedTool = toolState.getCurrentTool();

            float posX = Helper.convertPixelToMeters((float) e.getX());
            float posY = Helper.convertPixelPositionToHeightInStorage(storageController.getStorage(), (float) e.getY());

            switch (selectedTool) {
                case SELECT -> {
                    // Do nothing
                }
                case PICKCOLOR -> {

                }
                case BOARD -> {

                    Board board = new Board();

                    for (Field field : ReflectionUtils.getEditableFields(Board.class)) {
                        field.setAccessible(true);

                        try {
                            Type genericType = field.getGenericType();

                            Object value = toolState.getValue(new EntityField(Board.class, field));

                            if (genericType instanceof ParameterizedType pt) {
                                Type actualType = pt.getActualTypeArguments()[0];
                                if (actualType.equals(Float.class)) {
                                    field.set(board, new ObservableField<>((Float) value));
                                } else if (actualType.equals(SerializableColor.class)) {
                                    field.set(board, new ObservableField<>((SerializableColor) value));
                                }
                            }
                        } catch (IllegalArgumentException | IllegalAccessException e1) {

                            e1.printStackTrace();
                        }
                    }

                    board.setPosX(posX);
                    board.setPosY(posY);

                    storageController.addBoard(board);
                    storageView.hideGhostRectangle();
                }
                case SUPPORT -> {

                    Support support = new Support();

                    for (Field field : ReflectionUtils.getEditableFields(Support.class)) {
                        field.setAccessible(true);

                        try {
                            Type genericType = field.getGenericType();

                            Object value = toolState.getValue(new EntityField(Support.class, field));

                            if (genericType instanceof ParameterizedType pt) {
                                Type actualType = pt.getActualTypeArguments()[0];
                                if (actualType.equals(Float.class)) {
                                    field.set(support, new ObservableField<>((Float) value));
                                } else if (actualType.equals(SerializableColor.class)) {
                                    field.set(support, new ObservableField<>((SerializableColor) value));
                                }
                            }
                        } catch (IllegalArgumentException | IllegalAccessException e1) {

                            e1.printStackTrace();
                        }
                    }

                    support.setPosX(posX);
                    support.setPosY(posY);

                    storageController.addSupport(support);
                    storageView.hideGhostRectangle();
                }
                case ITEM -> {

                    Item item = new Item();

                    for (Field field : ReflectionUtils.getEditableFields(Item.class)) {
                        field.setAccessible(true);

                        try {
                            Type genericType = field.getGenericType();

                            Object value = toolState.getValue(new EntityField(Item.class, field));

                            if (genericType instanceof ParameterizedType pt) {
                                Type actualType = pt.getActualTypeArguments()[0];
                                if (actualType.equals(Float.class)) {
                                    field.set(item, new ObservableField<>((Float) value));
                                } else if (actualType.equals(SerializableColor.class)) {
                                    field.set(item, new ObservableField<>((SerializableColor) value));
                                }
                            }
                        } catch (IllegalArgumentException | IllegalAccessException e1) {

                            e1.printStackTrace();
                        }
                    }

                    item.setPosX(posX);
                    item.setPosY(posY);

                    storageController.addItem(item);
                    storageView.hideGhostRectangle();

                }
            }
        });

        storageView.setOnMouseMoved(e -> {

            switch (toolState.getCurrentTool()) {
                case SELECT -> {
                    storageView.hideGhostRectangle();
                }
                case PICKCOLOR -> {

                }
                default -> {
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
