package pizzapal.model.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import pizzapal.model.domain.core.Storage;
import pizzapal.utils.ToolState; // TODO: Think about this dependancy

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Support extends Entity {

    public int id;

    private Storage storage;

    private List<Board> boardsLeft;
    private List<Board> boardsRight;

    public Support() {
        super();
        boardsLeft = new ArrayList<>();
        boardsRight = new ArrayList<>();
    }

    public Support(Storage storage, float width, float height, float posX, float posY) {
        this(storage, new SerializableColor(ToolState.STANDARD_SUPPORT_COLOR), width, height, posX, posY);
    }

    public Support(SerializableColor color, float width, float height, float posX, float posY) {
        this(null, color, width, height, posX, posY);
    }

    public Support(float width, float height, float posX, float posY) {
        this(null, new SerializableColor(ToolState.STANDARD_SUPPORT_COLOR), width, height, posX, posY);
    }

    public Support(Storage storage, SerializableColor color, float width, float height, float posX, float posY) {
        super(color, width, height, posX, posY);
        boardsLeft = new ArrayList<>();
        boardsRight = new ArrayList<>();

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
            posXObservable().addListener(b.rightPosXListener);
            b.initListeners();
        }

        for (Board b : boardsRight) {
            posXObservable().addListener(b.leftPosXListener);
            b.initListeners();
        }
    }

    // TODO: Implement correctly
    public void delete() {

        storage.removeSupport(this);
        storage = null;

        for (Board b : boardsLeft) {
            b.setSupportRight(null);
        }

        for (Board b : boardsRight) {
            b.setSupportLeft(null);
        }

        super.delete();
    }

    public void edit(float width, float height, SerializableColor color) {
        setWidth(width);
        setHeight(height);
        setColor(color);
    }

    public void move(float posX) {
        setPosX(posX);
    }

    public void addBoardLeft(Board board) {
        boardsLeft.add(board);
    }

    public void addBoardRight(Board board) {
        boardsRight.add(board);
    }

    // GETTERS AND SETTERS

    public List<Board> getBoardsLeft() {
        return boardsLeft;
    }

    public void setBoardsLeft(List<Board> boardsLeft) {
        this.boardsLeft = boardsLeft;
    }

    public List<Board> getBoardsRight() {
        return boardsRight;
    }

    public void setBoardsRight(List<Board> boardsRight) {
        this.boardsRight = boardsRight;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Storage getStorage() {
        return storage;
    }

    @Override
    public String toString() {
        return "Support [width=" + width + ", height=" + height + ", positionX=" + posX + ", positionY="
                + posY + "]";
    }

}
