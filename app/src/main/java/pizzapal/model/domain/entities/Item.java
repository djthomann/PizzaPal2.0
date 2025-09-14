package pizzapal.model.domain.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import pizzapal.model.observability.FieldListener;
import pizzapal.model.observability.ObservableField;

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
public class Item extends FixedWidthEntity {

    public String id;

    // private Ingredient ingredient; // TODO: Implement differently

    @Editable
    private ObservableField<Float> weight;

    private float offsetX;

    private Board board;

    FieldListener<Float> posXListener = (obs, oldValue, newValue) -> {
        setPosX(newValue + offsetX);
    };

    FieldListener<Float> posYListener = (obs, oldValue, newValue) -> {
        setPosY(newValue + height.getValue());
    };

    public Item() {

    }

    public Item(SerializableColor color, float weight, float width, float height, float offsetX) {
        super(color, width, height, 0, 0);
        // color = Color.RED;
        this.weight = new ObservableField<Float>(weight);
        this.offsetX = offsetX;

        // ingredient = new Ingredient("Tomate", Color.RED); // TODO: implement
        // correctly
    }

    public Item(Board board, SerializableColor color, float weight, float width, float height, float offsetX) {
        super(color, width, height, board.getPosX() + offsetX, board.getPosY() + height);
        // color = Color.RED;
        this.weight = new ObservableField<Float>(weight);
        this.offsetX = offsetX;

        // ingredient = new Ingredient("Tomate", Color.RED); // TODO: implement
        // correctly

        if (board != null) {
            placeOn(board);
        }
    }

    public void initListeners() {
        addListeners(board);
    }

    public void placeOn(Board board) {
        this.board = board;

        setPosX(board.getPosX() + offsetX);
        setPosY(board.getPosY() + height.getValue());

        board.addItem(this);
        board.posXObservable().addListener(posXListener);
        board.posYObservable().addListener(posYListener);
    }

    public void edit(float width, float height, float weight, SerializableColor color) {
        setWeight(weight);
        setWidth(width);
        setHeight(height);
        setColor(color);
    }

    public void delete() {
        board.removeItem(this);
        removeListeners(board);

        super.delete();
    }

    public void addListeners(Board board) {
        board.posXObservable().addListener(posXListener);
        board.posYObservable().addListener(posYListener);
    }

    public void removeListeners(Board board) {
        board.posXObservable().removeListener(posXListener);
        board.posYObservable().removeListener(posYListener);
    }

    public void move(Board board, float offsetX) {
        this.board.removeItem(this);
        removeListeners(this.board);
        this.board = board;
        board.addItem(this);
        addListeners(board);
        this.offsetX = offsetX;
        setPosX(board.getPosX() + offsetX);
        setPosY(board.getPosY() + height.getValue());
    }

    // @JsonIgnore
    // public SerializableColor getColor() {
    // return ingredient.getColor();
    // }

    // public void setColor(SerializableColor color) {
    // ingredient.setColor(color);
    // }

    public ObservableField<Float> weightObservable() {
        return weight;
    }

    public float getWeight() {
        return weight.getValue();
    }

    public void setWeight(float weight) {
        this.weight.setValue(weight);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    // public Ingredient getIngredient() {
    // return ingredient;
    // }

    // public void setIngredient(Ingredient ingredient) {
    // this.ingredient = ingredient;
    // }

    public float getOffsetX() {
        return offsetX;
    }

}
