package pizzapal.model.listener.change;

import pizzapal.model.domain.entities.Item;
import pizzapal.model.listener.Listener;

public interface ItemChangeListener extends Listener {

    void onItemChange(Item item, ChangeType type);

}
