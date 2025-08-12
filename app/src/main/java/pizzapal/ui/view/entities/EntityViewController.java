package pizzapal.ui.view.entities;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import pizzapal.model.controller.StorageController;
import pizzapal.model.domain.entities.Entity;
import pizzapal.ui.UIConfig;
import pizzapal.utils.Helper;
import pizzapal.utils.ToolState;
import pizzapal.utils.ToolState.Tool;

public abstract class EntityViewController<E extends Entity> {

    private StorageController storageController;

    private ToolState toolState;

    protected EntityView<E> view;
    protected E entity;

    private Double offsetX;
    private Double offsetY;
    private boolean dragging = false;

    private final ContextMenu contextMenu;

    // TODO: calc Width height etc. in Subclasses with method that need to be
    // implemented and give these calc values to this superclass

    protected EntityViewController(StorageController storageController, ToolState toolState, E entity,
            EntityView<E> view) {
        this.storageController = storageController;
        this.toolState = toolState;
        this.entity = entity;
        this.view = view;

        contextMenu = new ContextMenu();
        initContextMenu();

        initDragAndDrop();
    }

    public void initContextMenu() {

        TextField tf1 = new TextField();
        tf1.setPrefWidth(150);
        tf1.setMaxWidth(150);
        tf1.setPromptText("New Width");
        tf1.setId("newWidthField");
        CustomMenuItem item1 = new CustomMenuItem(tf1);
        item1.setHideOnClick(false);

        TextField tf2 = new TextField();
        tf2.setPrefWidth(150);
        tf2.setMaxWidth(150);
        tf2.setPromptText("New Height");
        tf2.setId("newHeightField");
        CustomMenuItem item2 = new CustomMenuItem(tf2);
        item2.setHideOnClick(false);

        ColorPicker colorPicker = new ColorPicker(Color.RED);
        CustomMenuItem colorItem = new CustomMenuItem(colorPicker);
        colorItem.setHideOnClick(false);

        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(e -> {
            storageController.edit(entity, Float.parseFloat(tf1.getText()), Float.parseFloat(tf2.getText()),
                    colorPicker.getValue());
            ;
        });

        ImageView icon1 = new ImageView(Helper.loadImage("/icons/edit.png"));
        icon1.setFitWidth(16);
        icon1.setFitHeight(16);
        editItem.setGraphic(icon1);
        editItem.setStyle("-fx-font-weight: bold;");

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> {
            storageController.delete(entity);
        });

        ImageView icon2 = new ImageView(Helper.loadImage("/icons/trash.png"));
        icon2.setFitWidth(16);
        icon2.setFitHeight(16);
        deleteItem.setGraphic(icon2);
        deleteItem.setStyle("-fx-font-weight: bold;");

        contextMenu.getItems().addAll(item1, item2, colorItem, editItem, new SeparatorMenuItem(), deleteItem);
    }

    public void showEditOptions() {

    }

    public void showContextMenu() {

        hideDropShadow();

        Bounds bounds = view.localToScreen(view.getBoundsInLocal());

        double x = bounds.getMinX() + view.widthProperty().get() + UIConfig.CONTEXT_MENU_OFFSET_PIXEL;
        double y = bounds.getMinY();

        contextMenu.show(view, x, y);
    }

    public void hideContextMenu() {
        contextMenu.hide();
    }

    public void toggleContextMenu() {
        if (contextMenu.isShowing()) {
            hideContextMenu();
        } else {

            showContextMenu();

        }
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public void initDragAndDrop() {

        // TODO: Cleanup

        view.setOnMousePressed(e -> {

            MouseButton button = e.getButton();

            switch (toolState.getCurrentTool()) {
                case SELECT -> {
                    switch (button) {
                        case PRIMARY -> {
                            dragging = true;
                            offsetX = e.getX();
                            offsetY = e.getY();
                            System.out.println("Pressed");
                            view.setCursor(Cursor.MOVE);
                        }
                        case SECONDARY -> {
                            toggleContextMenu();
                        }
                        default -> {
                        }
                    }
                }
                case PICKCOLOR -> {
                    toolState.setColor(entity, view.getColor());
                }
                default -> {
                    // Don't react to other tools
                    return;
                }
            }

        });

        view.setOnMouseDragged(e -> {

            MouseButton button = e.getButton();
            view.toFront();

            switch (toolState.getCurrentTool()) {
                case SELECT -> {
                    switch (button) {
                        case PRIMARY -> {
                            if (dragging) {

                                view.moveRectangle((float) (e.getX() - offsetX), (float) (e.getY() - offsetY));
                            }
                        }
                        default -> {
                        }
                    }
                }
                default -> {
                    // Don't react to other tools
                    return;
                }
            }

        });

        view.setOnMouseReleased(e -> {

            MouseButton button = e.getButton();

            switch (toolState.getCurrentTool()) {
                case SELECT -> {
                    switch (button) {
                        case PRIMARY -> {

                            if (dragging) {
                                Point2D localPoint = view.getParent().sceneToLocal(e.getSceneX(), e.getSceneY());
                                float xInView = (float) (localPoint.getX() - offsetX);
                                float yInView = (float) (localPoint.getY() - offsetY);

                                offsetX = 0D;
                                offsetY = 0D;

                                onMouseReleased(xInView, yInView);
                                dragging = false;
                            }

                        }
                        default -> {
                        }
                    }
                }
                default -> {
                    // Don't react to other tools
                    return;
                }
            }

        });

        view.setOnMouseEntered(e -> {
            if (toolState.getCurrentTool() == Tool.SELECT) {
                view.setCursor(Cursor.MOVE);
                showDropShadow();
            }
        });

        view.setOnMouseExited(e -> {
            view.setCursor(Cursor.DEFAULT);
            hideDropShadow();
        });
    }

    private void showDropShadow() {
        view.getEntityRectangle().setEffect(new DropShadow(15,
                view.getColor().brighter()));
    }

    private void hideDropShadow() {
        view.getEntityRectangle().setEffect(null);
    }

    protected abstract void onMouseReleased(float xInView, float yInView);

    public Pane getView() {
        return view;
    }

}
