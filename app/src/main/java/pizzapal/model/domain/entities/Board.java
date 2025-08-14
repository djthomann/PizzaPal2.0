package pizzapal.model.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javafx.scene.paint.Color;
import pizzapal.model.observability.FieldListener;
import pizzapal.utils.ToolState;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Board extends Entity {

    public String id;

    private Support supportLeft;

    private Support supportRight;

    private List<Item> items = new ArrayList<>();

    private SerializableColor color;
    // public static final Color STANDARD_COLOR = ToolState.STANDARD_BOARD_COLOR;

    // Relative to Support
    private float offsetY;

    @JsonIgnore
    FieldListener<Float> leftPosXListener = (obs, oldValue, newValue) -> {
        setPosX(newValue + supportLeft.getWidth());
        setWidth(supportRight.getPosX() - supportLeft.getWidth() - newValue);
    };

    @JsonIgnore
    FieldListener<Float> rightPosXListener = (obs, oldValue, newValue) -> {
        setWidth(newValue - supportLeft.getPosX() - supportLeft.getWidth());
    };

    public Board() {
        items = new ArrayList<>();
    }

    public Board(Support supportLeft, Support supportRight, float height, float offsetY) {
        this(supportLeft, supportRight, height, offsetY, ToolState.STANDARD_BOARD_COLOR);
    }

    public Board(float height, float offsetY, Color color) {
        super(0, height, 0, 0);
        this.offsetY = offsetY;
        this.color = new SerializableColor(color);
    }

    public Board(Support supportLeft, Support supportRight, float height, float offsetY, Color color) {
        super(supportRight.getPosX() - supportLeft.getPosX()
                + supportLeft.getWidth(), height, supportLeft.getPosX(), supportLeft.getHeight() - offsetY);
        this.offsetY = offsetY;
        this.color = new SerializableColor(color);

        if (supportLeft != null && supportRight != null) {
            attach(supportLeft, supportRight);
        }

        items = new ArrayList<>();

    }

    public void initListeners() {
        for (Item i : items) {
            i.initListeners();
        }
    }

    public void attach(Support supportLeft, Support supportRight) {

        this.supportLeft = supportLeft;
        this.supportRight = supportRight;

        setWidth(getWidth());
        setPosX(supportLeft.getPosX() + supportLeft.getWidth());
        setPosY(supportLeft.getHeight() - offsetY);

        supportLeft.posXObservable().addListener(leftPosXListener);
        supportLeft.addBoardRight(this);

        supportRight.posXObservable().addListener(rightPosXListener);
        supportRight.addBoardLeft(this);

    }

    public void edit(float height, SerializableColor color) {
        setHeight(height);
        setColor(color);
    }

    public void delete() {

        supportLeft.getBoardsRight().remove(this);
        supportLeft.posXObservable().removeListener(leftPosXListener);

        supportRight.getBoardsLeft().remove(this);
        supportRight.posXObservable().removeListener(rightPosXListener);

        List<Item> toDelete = new ArrayList<>(items);
        for (Item i : toDelete) {
            i.delete();
        }
        items.clear();

        super.delete();
    }

    public void addItem(Item item) {
        items.add(item);
        item.addListeners(this);
    }

    public void removeItem(Item item) {
        items.remove(item);
        item.removeListeners(this);
    }

    public void move(Support left, Support right, float offsetY) {

        if (supportLeft != left) {
            supportLeft.posXObservable().removeListener(leftPosXListener);
            supportLeft.getBoardsRight().remove(this);
            left.posXObservable().addListener(leftPosXListener);
            left.getBoardsRight().add(this);
            supportLeft = left;
        }

        if (supportRight != right) {
            supportRight.posXObservable().removeListener(rightPosXListener);
            supportRight.getBoardsLeft().remove(this);
            right.posXObservable().addListener(rightPosXListener);
            right.getBoardsLeft().add(this);
            supportRight = right;
        }

        this.offsetY = offsetY;
        setPosY(supportLeft.getHeight() - offsetY);
        setPosX(left.getPosX() + left.getWidth());
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public float getWidth() {
        float width = supportRight.getPosX() - supportLeft.getPosX()
                - supportLeft.getWidth();
        return width;
    }

    public Support getSupportLeft() {
        return supportLeft;
    }

    public void setSupportLeft(Support supportLeft) {
        this.supportLeft = supportLeft;
    }

    public Support getSupportRight() {
        return supportRight;
    }

    public void setSupportRight(Support supportRight) {
        this.supportRight = supportRight;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public SerializableColor getColor() {
        return color;
    }

    public void setColor(SerializableColor color) {
        this.color = color;
        // notifyListeners(ChangeType.EDIT);
    }

}
