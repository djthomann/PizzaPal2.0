package pizzapal.model.controller;

import pizzapal.model.domain.entities.Support;

public interface SupportCreationListener extends CreationListener {
    void onSupportCreated(Support support);
}
