package pizzapal.model.domain.entities;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import pizzapal.model.observability.BoardChangeListener;
import pizzapal.model.observability.ItemChangeListener;
import pizzapal.model.observability.Observable;

public class Item extends Entity implements Observable<ItemChangeListener> {

    private Color color;

    private final float weight;

    private float offsetX;

    private Board board;

    private List<ItemChangeListener> listeners = new ArrayList<>();

    private BoardChangeListener listener = (model, type) -> {
        reactToBoardChange(model);
    };

    public Item(Board board, Color color, float weight, float width, float height, float offsetX) {
        super(width, height, board.getPosX() + offsetX, board.getPosY());
        this.board = board;
        this.color = color;
        this.weight = weight;
        this.offsetX = offsetX;

        board.addListener(listener);
    }

    private void notifyListeners() {
        for (ItemChangeListener l : listeners) {
            l.onItemChange(this);
        }
    }

    public void move(Board board, float offsetX) {
        this.board.removeItem(this);
        board.removeListener(listener);
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
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
        notifyListeners();
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
        notifyListeners();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
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
