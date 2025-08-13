package pizzapal.model.listener.change;

import pizzapal.model.domain.entities.Board;
import pizzapal.model.listener.Listener;

public interface BoardChangeListener extends Listener {

    void onBoardChange(Board board, ChangeType type);

}
