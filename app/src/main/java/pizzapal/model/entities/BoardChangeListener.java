package pizzapal.model.entities;

public interface BoardChangeListener extends Listener {

    void onBoardChange(Board board, ChangeType type);

}
