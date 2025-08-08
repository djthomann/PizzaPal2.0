package pizzapal.ui.view.entities;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
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

        // contextMenu.setStyle("-fx-background-color: black; -fx-border-color: black;
        // -fx-border-width: 1;");

        MenuItem item1 = new MenuItem("Option 1");
        item1.setOnAction(e -> System.out.println("Option 1 geklickt"));

        ImageView icon1 = new ImageView(Helper.loadImage("/icons/edit.png"));
        icon1.setFitWidth(16);
        icon1.setFitHeight(16);
        item1.setGraphic(icon1);
        item1.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");

        MenuItem item2 = new MenuItem("Delete");
        item2.setOnAction(e -> {
            storageController.delete(entity);
        });

        ImageView icon2 = new ImageView(Helper.loadImage("/icons/trash.png"));
        icon2.setFitWidth(16);
        icon2.setFitHeight(16);
        item2.setGraphic(icon2);
        item2.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");

        contextMenu.getItems().addAll(item1, item2);
    }

    public void showContextMenu() {

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

        view.setOnMouseEntered(_ -> {
            if (toolState.getCurrentTool() == Tool.SELECT) {
                view.setCursor(Cursor.MOVE);
                view.getEntityRectangle().setEffect(new DropShadow(15, view.getColor().brighter()));
            }
        });

        view.setOnMouseExited(_ -> {
            view.setCursor(Cursor.DEFAULT);
            view.getEntityRectangle().setEffect(null);
        });
    }

    protected abstract void onMouseReleased(float xInView, float yInView);

    public Pane getView() {
        return view;
    }

}
