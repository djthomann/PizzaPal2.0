package pizzapal.model.observability;

import pizzapal.model.domain.entities.Item;

public interface ItemChangeListener extends Listener {

    void onItemChange(Item item, ChangeType type);

}
