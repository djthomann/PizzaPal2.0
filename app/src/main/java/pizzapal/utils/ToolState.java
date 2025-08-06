package pizzapal.utils;

import javafx.scene.paint.Color;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Entity;
import pizzapal.model.domain.entities.Ingredient;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;
import pizzapal.model.repository.IngredientRepository;

public class ToolState {

    // TODO: Make static fields invisible?

    private Tool currentTool;

    private float supportWidth;
    public static final float STANDARD_SUPPORT_WIDTH = 0.25f;
    private float supportHeight;
    public static final float STANDARD_SUPPORT_HEIGHT = 2f;
    private Color supportColor;
    public static final Color STANDARD_SUPPORT_COLOR = Color.BROWN;

    private float boardHeight;
    public static final float STANDARD_BOARD_HEIGHT = 0.15f;
    public Color boardColor;
    public static final Color STANDARD_BOARD_COLOR = Color.BURLYWOOD;

    private float itemWidth;
    public static final float STANDARD_ITEM_WIDTH = 0.4f;
    private float itemHeight;
    public static final float STANDARD_ITEM_HEIGHT = 0.4f;
    private float itemWeight;
    public static final float STANDARD_ITEM_WEIGHT = 0.5f;
    private Ingredient itemIngredient;
    public static final Ingredient STANDARD_ITEM_INGREDIENT = IngredientRepository.getInstance()
            .getIngredient("Tomato");

    public enum Tool {
        SELECT, PICKCOLOR, SUPPORT, BOARD, ITEM
    }

    public ToolState() {
        currentTool = Tool.SELECT;
        supportWidth = STANDARD_SUPPORT_WIDTH;
        supportHeight = STANDARD_SUPPORT_HEIGHT;
        supportColor = STANDARD_SUPPORT_COLOR;
        boardHeight = STANDARD_BOARD_HEIGHT;
        boardColor = STANDARD_BOARD_COLOR;
        itemWidth = STANDARD_ITEM_WIDTH;
        itemHeight = STANDARD_ITEM_HEIGHT;
        itemWeight = STANDARD_ITEM_WEIGHT;
    }

    public void setColor(Entity e, Color color) {
        if (e instanceof Board) {
            setBoardColor(color);
        } else if (e instanceof Support) {
            setSupportColor(color);
        } else if (e instanceof Item i) {
            // implement?
        }
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

    public Color getSupportColor() {
        return supportColor;
    }

    public void setSupportColor(Color supportColor) {
        this.supportColor = supportColor;
    }

    public float getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(float boardHeight) {
        this.boardHeight = boardHeight;
    }

    public Color getBoardColor() {
        return boardColor;
    }

    public void setBoardColor(Color boardColor) {
        this.boardColor = boardColor;
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

    public Ingredient getItemIngredient() {
        return itemIngredient;
    }

    public void setItemIngredient(Ingredient itemIngredient) {
        this.itemIngredient = itemIngredient;
    }

    public float getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(float itemWeight) {
        this.itemWeight = itemWeight;
    }

}
