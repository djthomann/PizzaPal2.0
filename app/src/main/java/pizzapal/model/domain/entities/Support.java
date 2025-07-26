package pizzapal.model.domain.entities;

import java.util.ArrayList;
import java.util.List;

import pizzapal.model.domain.core.Storage;
import pizzapal.model.observability.ChangeType;
import pizzapal.model.observability.Observable;
import pizzapal.model.observability.SupportChangeListener;

public class Support implements Observable<SupportChangeListener> {

    private final List<SupportChangeListener> listeners = new ArrayList<>();

    private final Storage storage;

    private List<Board> boardsLeft;

    private List<Board> boardsRight;

    private final float width;

    private final float height;

    private float positionX;

    private float positionY;

    public Support(Storage storage, float width, float height, float positionX, float positionY) {
        this.storage = storage;
        boardsLeft = new ArrayList<>();
        boardsRight = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void delete() {
        notifyListeners(ChangeType.DELETE);
    }

    public List<Board> getBoardsLeft() {
        return boardsLeft;
    }

    public void setBoardsLeft(List<Board> boardsLeft) {
        this.boardsLeft = boardsLeft;
    }

    public void addBoardLeft(Board board) {
        boardsLeft.add(board);
    }

    public List<Board> getBoardsRight() {
        return boardsRight;
    }

    public void setBoardsRight(List<Board> boardsRight) {
        this.boardsRight = boardsRight;
    }

    public void addBoardRight(Board board) {
        boardsRight.add(board);
    }

    public void move(float posX) {
        setPositionX(posX);
    }

    public void addListener(SupportChangeListener l) {
        listeners.add(l);
    }

    public void removeListener(SupportChangeListener l) {
        listeners.remove(l);
    }

    private void notifyListeners(ChangeType type) {
        for (SupportChangeListener l : listeners) {
            l.onSupportChange(this, type);
        }
    }

    private void notifyListeners() {
        for (SupportChangeListener l : listeners) {
            l.onSupportChange(this, ChangeType.MOVE);
        }
    }

    // GETTERS AND SETTERS

    public float getWidth() {
        return width;
    }

    public Storage getStorage() {
        return storage;
    }

    public float getHeight() {
        return height;
    }

    public float getPositionX() {
        return positionX;
    }

    public void setPositionX(float positionX) {
        this.positionX = positionX;
        notifyListeners();
    }

    public float getPositionY() {
        return positionY;
    }

    public void setPositionY(float positionY) {
        this.positionY = positionY;
        notifyListeners();
    }

    @Override
    public String toString() {
        return "Support [width=" + width + ", height=" + height + ", positionX=" + positionX + ", positionY="
                + positionY + "]";
    }

}
