package pizzapal.model.controller;

import pizzapal.model.domain.entities.Item;

public interface ItemCreationListener extends CreationListener {
    void onItemCreate(Item item);
}
