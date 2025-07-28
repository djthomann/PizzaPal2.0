package pizzapal.utils;

public class ToolState {

    private Tool currentTool;

    private float supportWidth;
    public static final float STANDARD_SUPPORT_WIDTH = 0.25f;
    private float supportHeight;
    public static final float STANDARD_SUPPORT_HEIGHT = 2f;

    private float boardHeight;
    public static final float STANDARD_BOARD_HEIGHT = 0.15f;

    private float itemWidth;
    public static final float STANDARD_ITEM_WIDTH = 0.4f;

    private float itemHeight;
    public static final float STANDARD_ITEM_HEIGHT = 0.4f;

    public enum Tool {
        SELECT, SUPPORT, BOARD, ITEM
    }

    public ToolState() {
        currentTool = Tool.SELECT;
        supportWidth = STANDARD_SUPPORT_WIDTH;
        supportHeight = STANDARD_SUPPORT_HEIGHT;
        boardHeight = STANDARD_BOARD_HEIGHT;
        itemWidth = STANDARD_ITEM_WIDTH;
        itemHeight = STANDARD_ITEM_HEIGHT;
    }

    public void setCurrentTool(Tool currentTool) {
        this.currentTool = currentTool;
    }

    public Tool getCurrentTool() {
        return currentTool;
    }

    public float getSupportWidth() {
        return supportWidth;
    }

    public void setSupportWidth(float supportWidth) {
        this.supportWidth = supportWidth;
    }

    public float getSupportHeight() {
        return supportHeight;
    }

    public void setSupportHeight(float supportHeight) {
        this.supportHeight = supportHeight;
    }

    public float getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(float boardHeight) {
        this.boardHeight = boardHeight;
    }

    public float getItemWidth() {
        return itemWidth;
    }

    public void setItemWidth(float itemWidth) {
        this.itemWidth = itemWidth;
    }

    public float getItemHeight() {
        return itemHeight;
    }

    public void setItemHeight(float itemHeight) {
        this.itemHeight = itemHeight;
    }

}
