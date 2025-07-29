package pizzapal.model.domain.entities;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.observability.ChangeType;
import pizzapal.model.observability.Observable;
import pizzapal.model.observability.SupportChangeListener;
import pizzapal.utils.ToolState;

public class Support extends Entity implements Observable<SupportChangeListener> {

    private final List<SupportChangeListener> listeners = new ArrayList<>();

    private Storage storage;

    private List<Board> boardsLeft;

    private List<Board> boardsRight;

    private Color color;
    private static final Color STANDARD_COLOR = ToolState.STANDARD_SUPPORT_COLOR;

    public Support(Storage storage, float width, float height, float posX, float posY) {
        this(storage, STANDARD_COLOR, width, height, posX, posY);
    }

    public Support(Storage storage, Color color, float width, float height, float posX, float posY) {
        super(width, height, posX, posY);
        this.storage = storage;
        boardsLeft = new ArrayList<>();
        boardsRight = new ArrayList<>();
        this.color = color;
        storage.addSupport(this);
    }

    public void delete() {

        for (Board b : boardsLeft) {
            b.setSupportRight(null);
        }

        for (Board b : boardsRight) {
            b.setSupportLeft(null);
        }

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
        setPosX(posX);
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

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setPosX(float positionX) {
        super.setPosX(positionX);
        notifyListeners();
    }

    public void setPosY(float positionY) {
        super.setPosY(positionY);
        notifyListeners();
    }

    @Override
    public String toString() {
        return "Support [width=" + width + ", height=" + height + ", positionX=" + posX + ", positionY="
                + posY + "]";
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
