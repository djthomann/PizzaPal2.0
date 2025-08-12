package pizzapal.model.domain.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javafx.scene.paint.Color;
import pizzapal.model.observability.BoardChangeListener;
import pizzapal.model.observability.ChangeType;
import pizzapal.model.observability.ItemChangeListener;
import pizzapal.model.observability.Observable;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Item extends Entity implements Observable<ItemChangeListener> {

    public String id;

    private Ingredient ingredient;

    private float weight;

    private float offsetX;

    private Board board;

    @JsonIgnore
    private List<ItemChangeListener> listeners = new ArrayList<>();

    @JsonIgnore
    private BoardChangeListener listener = (model, type) -> {
        switch (type) {
            case MOVE -> {
                reactToBoardChange(model);
            }
            case DELETE -> {
                delete();
            }
        }
    };

    public Item() {

    }

    public Item(Color color, float weight, float width, float height, float offsetX) {
        super(width, height, 0, 0);
        color = Color.RED;
        this.weight = weight;
        this.offsetX = offsetX;

        ingredient = new Ingredient("Tomate", Color.RED); // TODO: implement correctly
    }

    public Item(Board board, Color color, float weight, float width, float height, float offsetX) {
        super(width, height, board.getPosX() + offsetX, board.getPosY() + height);
        color = Color.RED;
        this.weight = weight;
        this.offsetX = offsetX;

        ingredient = new Ingredient("Tomate", Color.RED); // TODO: implement correctly

        if (board != null) {
            placeOn(board);
        }
    }

    private void notifyListeners(ChangeType type) {
        for (ItemChangeListener l : listeners) {
            l.onItemChange(this, type);
        }
    }

    public void placeOn(Board board) {
        this.board = board;

        setPosX(board.getPosX() + offsetX);
        setPosY(board.getPosY() + height);

        board.addItem(this);
        board.addListener(listener);
    }

    public void edit(float width, float height, SerializableColor color) {
        setWidth(width);
        setHeight(height);
        setColor(color);
    }

    public void delete() {
        setBoard(null);
        // TODO: cleanup other items on top, not yet implemented
        notifyListeners(ChangeType.DELETE);
    }

    public void move(Board board, float offsetX) {
        this.board.removeItem(this);
        this.board.removeListener(listener);
        this.board = board;
        board.addItem(this);
        board.addListener(listener);
        this.offsetX = offsetX;
        setPosX(board.getPosX() + offsetX);
        setPosY(board.getPosY() + height);
    }

    public void reactToBoardChange(Board board) {
        setPosX(board.getPosX() + offsetX);
        setPosY(board.getPosY() + height);
    }

    @JsonIgnore
    public SerializableColor getColor() {
        return ingredient.getColor();
    }

    public void setColor(SerializableColor color) {
        ingredient.setColor(color);
        notifyListeners(ChangeType.EDIT);
    }

    public float getWeight() {
        return weight;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
        notifyListeners(ChangeType.MOVE);
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
        notifyListeners(ChangeType.MOVE);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public float getOffsetX() {
        return offsetX;
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
    public void addListener(ItemChangeListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListener(ItemChangeListener l) {
        listeners.remove(l);
    }

    public BoardChangeListener getListener() {
        return listener;
    }

}
