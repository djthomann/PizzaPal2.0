package pizzapal.model.listener.create;

import pizzapal.model.domain.entities.Item;

public interface ItemCreationListener extends CreationListener {
    void onItemCreate(Item item);
}
