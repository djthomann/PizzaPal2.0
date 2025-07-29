package pizzapal.model.domain.entities;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import pizzapal.model.observability.BoardChangeListener;
import pizzapal.model.observability.ChangeType;
import pizzapal.model.observability.Observable;
import pizzapal.model.observability.SupportChangeListener;

public class Board extends Entity implements Observable<BoardChangeListener> {

    private final List<BoardChangeListener> listeners = new ArrayList<>();

    private Support supportLeft;

    private Support supportRight;

    private List<Item> items = new ArrayList<>();

    private Color color;

    SupportChangeListener leftListener = (model, type) -> {
        switch (type) {
            case MOVE -> {
                reactToChangeLeft(model);
            }
            case DELETE -> {
                delete();
            }
        }
    };

    SupportChangeListener rightListener = (model, type) -> {
        reactToChangeRight(model);
    };

    // Relative to Support
    private float offsetY;

    public Board(Support supportLeft, Support supportRight, float height, float offsetY, Color color) {
        super(supportRight.getPosX() - supportLeft.getPosX()
                + supportLeft.getWidth(), height, supportLeft.getPosX(), supportLeft.getHeight() - offsetY);
        this.supportLeft = supportLeft;
        this.supportRight = supportRight;
        this.height = height;
        this.posY = supportLeft.getHeight() - offsetY;
        this.offsetY = offsetY;
        this.posX = supportLeft.getPosX();
        this.color = color;

        supportLeft.addListener(leftListener);
        supportLeft.addBoardRight(this);

        supportRight.addListener(rightListener);
        supportRight.addBoardLeft(this);

    }

    public void delete() {
        notifyListeners(ChangeType.DELETE);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void reactToChangeLeft(Support support) {
        setPosX(support.getPosX() + support.getWidth());
        setPosY(supportLeft.getHeight() - offsetY);
        // setPosY(Helper.getPixelPositionYInStorage(support.getStorage(), support));
    }

    public void reactToChangeRight(Support support) {
        notifyListeners();
    }

    @Override
    public void addListener(BoardChangeListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListener(BoardChangeListener l) {
        listeners.remove(l);
    }

    private void notifyListeners(ChangeType type) {
        for (BoardChangeListener l : listeners) {
            l.onBoardChange(this, type);
        }
    }

    private void notifyListeners() {
        for (BoardChangeListener l : listeners) {
            l.onBoardChange(this, ChangeType.MOVE);
        }
    }

    public void move(Support left, Support right, float offsetY) {

        if (supportLeft != left) {
            supportLeft.removeListener(leftListener);
            supportLeft.getBoardsRight().remove(this);
            left.addListener(leftListener);
            left.getBoardsRight().add(this);
            supportLeft = left;
        }

        if (supportRight != right) {
            supportRight.removeListener(rightListener);
            supportRight.getBoardsLeft().remove(this);
            right.addListener(rightListener);
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

    public void setPosY(float posY) {
        super.setPosY(posY);
        notifyListeners();
    }

    public void setPosX(float posX) {
        super.setPosX(posX);
        notifyListeners();
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
