package pizzapal.model.entities;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import pizzapal.Helper;

public class Item implements Observable<ItemChangeListener> {

    private Color color;

    private final float weight;

    private final float width;

    private final float height;

    private float posX;

    private float posY;

    private final float offsetX;

    private Board board;

    private List<ItemChangeListener> listeners = new ArrayList<>();

    private BoardChangeListener listener = model -> {
        reactToBoardChange(model);
    };

    public Item(Board board, Color color, float weight, float width, float height, float offsetX) {
        this.board = board;
        this.color = color;
        this.weight = weight;
        this.width = width;
        this.height = height;
        this.posX = board.getPosX() + offsetX;
        posY = board.getPosY();
        this.offsetX = offsetX;

        board.addListener(listener);
    }

    private void notifyListeners() {
        for (ItemChangeListener l : listeners) {
            l.onItemChange(this);
        }
    }

    public void move(Board board) {
        this.board.removeItem(this);
        this.board = board;
        board.addItem(this);
        // TODO notify
    }

    public void reactToBoardChange(Board board) {
        setPosX(board.getPosX() + Helper.convertMetersToPixel(offsetX));
        setPosY(board.getPosY());
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
