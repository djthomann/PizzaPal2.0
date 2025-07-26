package pizzapal.model.observability;

import pizzapal.model.domain.entities.Board;

public interface BoardChangeListener extends Listener {

    void onBoardChange(Board board, ChangeType type);

}
