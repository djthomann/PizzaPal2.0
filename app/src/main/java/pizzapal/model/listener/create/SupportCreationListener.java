package pizzapal.model.listener.create;

import pizzapal.model.domain.entities.Support;

public interface SupportCreationListener extends CreationListener {
    void onSupportCreated(Support support);
}
