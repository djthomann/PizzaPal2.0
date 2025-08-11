package pizzapal.model.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javafx.scene.paint.Color;
import pizzapal.model.domain.core.Storage;
import pizzapal.model.observability.ChangeType;
import pizzapal.model.observability.Observable;
import pizzapal.model.observability.SupportChangeListener;
import pizzapal.utils.ToolState;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Support extends Entity implements Observable<SupportChangeListener> {

    public int id;

    @JsonIgnore
    private final List<SupportChangeListener> listeners = new ArrayList<>();

    private Storage storage;

    private List<Board> boardsLeft;
    private List<Board> boardsRight;

    private SerializableColor color;
    // private static final Color STANDARD_COLOR = ToolState.STANDARD_SUPPORT_COLOR;

    public Support() {
        super();
        boardsLeft = new ArrayList<>();
        boardsRight = new ArrayList<>();
    }

    public Support(Storage storage, float width, float height, float posX, float posY) {
        this(storage, ToolState.STANDARD_SUPPORT_COLOR, width, height, posX, posY);
    }

    public Support(Color color, float width, float height, float posX, float posY) {
        this(null, color, width, height, posX, posY);
    }

    public Support(float width, float height, float posX, float posY) {
        this(null, ToolState.STANDARD_SUPPORT_COLOR, width, height, posX, posY);
    }

    public Support(Storage storage, Color color, float width, float height, float posX, float posY) {
        super(width, height, posX, posY);
        boardsLeft = new ArrayList<>();
        boardsRight = new ArrayList<>();
        this.color = new SerializableColor(color);

        if (storage != null) {
            putInStorage(storage);
        }
    }

    public void putInStorage(Storage storage) {
        this.storage = storage;
        storage.addSupport(this);
    }

    public void initListeners() {
        for (Board b : boardsLeft) {
            listeners.add(b.rightListener);
            b.initListeners();
        }

        for (Board b : boardsRight) {
            listeners.add(b.leftListener);
            b.initListeners();
        }
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

    public void edit(float width, float height, SerializableColor color) {
        setWidth(width);
        setHeight(height);
        setColor(color);
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

    public void setWidth(float width) {
        super.setWidth(width);
        notifyListeners(ChangeType.EDIT);
    }

    public void setHeight(float height) {
        super.setHeight(height);
        notifyListeners(ChangeType.EDIT);
    }

    @Override
    public String toString() {
        return "Support [width=" + width + ", height=" + height + ", positionX=" + posX + ", positionY="
                + posY + "]";
    }

    public SerializableColor getColor() {
        return color;
    }

    public void setColor(SerializableColor color) {
        this.color = color;
        notifyListeners(ChangeType.EDIT);
    }

}
