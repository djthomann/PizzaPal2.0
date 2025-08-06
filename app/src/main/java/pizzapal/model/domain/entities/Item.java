package pizzapal.model.domain.entities;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import pizzapal.model.observability.BoardChangeListener;
import pizzapal.model.observability.ChangeType;
import pizzapal.model.observability.ItemChangeListener;
import pizzapal.model.observability.Observable;

public class Item extends Entity implements Observable<ItemChangeListener> {

    private Ingredient ingredient;

    private final float weight;

    private float offsetX;

    private Board board;

    private List<ItemChangeListener> listeners = new ArrayList<>();

    private BoardChangeListener listener = (model, type) -> {
        switch (type) {
            case MOVE -> {
                reactToBoardChange(model);
            }
            case DELETE -> {
                delete();
            }
        }
    };

    public Item(Board board, Color color, float weight, float width, float height, float offsetX) {
        super(width, height, board.getPosX() + offsetX, board.getPosY() + height);
        this.board = board;
        color = Color.RED;
        this.weight = weight;
        this.offsetX = offsetX;

        ingredient = new Ingredient("Tomate", Color.RED); // TODO: implement correctly
    }

    private void notifyListeners(ChangeType type) {
        for (ItemChangeListener l : listeners) {
            l.onItemChange(this, type);
        }
    }

    public void place() {
        board.addItem(this);
        board.addListener(listener);
    }

    public void delete() {
        // TODO: cleanup other items on top, not yet implemented
        notifyListeners(ChangeType.DELETE);
    }

    public void move(Board board, float offsetX) {
        this.board.removeItem(this);
        this.board.removeListener(listener);
        this.board = board;
        board.addItem(this);
        board.addListener(listener);
        this.offsetX = offsetX;
        setPosX(board.getPosX() + offsetX);
        setPosY(board.getPosY() + height);
    }

    public void reactToBoardChange(Board board) {
        setPosX(board.getPosX() + offsetX);
        setPosY(board.getPosY() + height);
    }

    public Color getColor() {
        return ingredient.getColor();
    }

    public float getWeight() {
        return weight;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
        notifyListeners(ChangeType.MOVE);
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
        notifyListeners(ChangeType.MOVE);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public float getOffsetX() {
        return offsetX;
    }

    @Override
    public void addListener(ItemChangeListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListener(ItemChangeListener l) {
        listeners.remove(l);
    }

}
