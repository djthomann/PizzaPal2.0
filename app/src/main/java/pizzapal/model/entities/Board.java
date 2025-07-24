package pizzapal.model.entities;

import java.util.ArrayList;
import java.util.List;

import pizzapal.Helper;

public class Board implements Observable<BoardChangeListener> {

    private final List<BoardChangeListener> listeners = new ArrayList<>();

    private Support supportLeft;

    private Support supportRight;

    // Relative to Support
    private float posY;

    private float posX;

    private final float height;

    public Board(Support supportLeft, Support supportRight, float height, float posY) {
        this.supportLeft = supportLeft;
        this.supportRight = supportRight;
        this.height = height;
        this.posY = posY;
        this.posX = supportLeft.getPositionX();
    }

    @Override
    public void addListener(BoardChangeListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListener(BoardChangeListener l) {
        listeners.remove(l);
    }

    private void notifyListeners() {
        for (BoardChangeListener l : listeners) {
            l.onBoardChange(this);
        }
    }

    public void move(Support left, Support right, float posY) {

        supportLeft = left;
        supportRight = right;

        System.out.println("STützen:" + left.toString() + "|" + right.toString());

        setPosY(posY);
        setPosX(left.getPositionX() + Helper.convertMetersToPixel(left.getWidth()));
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        float width = supportRight.getPositionX() - supportLeft.getPositionX()
                - Helper.convertMetersToPixel(supportLeft.getWidth());

        System.out.println(width);
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

}
