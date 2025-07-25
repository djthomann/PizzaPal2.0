package pizzapal.model.domain.entities;

import java.util.ArrayList;
import java.util.List;

import pizzapal.model.observability.BoardChangeListener;
import pizzapal.model.observability.ChangeType;
import pizzapal.model.observability.Observable;
import pizzapal.model.observability.SupportChangeListener;

public class Board implements Observable<BoardChangeListener> {

    private final List<BoardChangeListener> listeners = new ArrayList<>();

    private Support supportLeft;

    private Support supportRight;

    private List<Item> items = new ArrayList<>();

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
    private float posY;

    private float posX;

    private float offsetY;

    private final float height;

    public Board(Support supportLeft, Support supportRight, float height, float offsetY) {
        this.supportLeft = supportLeft;
        this.supportRight = supportRight;
        this.height = height;
        this.posY = supportLeft.getHeight() - offsetY;
        this.offsetY = offsetY;
        this.posX = supportLeft.getPositionX();

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
        setPosX(support.getPositionX() + support.getWidth());
        System.out.println(offsetY);
        setPosY(supportLeft.getHeight() + offsetY);
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
        setPosY(supportLeft.getPositionY() + offsetY);
        setPosX(left.getPositionX() + left.getWidth());
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        float width = supportRight.getPositionX() - supportLeft.getPositionX()
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

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
        notifyListeners();
    }

    public float getPosX() {
        return posX;
    }

    private void setPosX(float posX) {
        this.posX = posX;
        notifyListeners();
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

}
