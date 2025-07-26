package pizzapal.model.observability;

import pizzapal.model.domain.entities.Support;

public interface SupportChangeListener extends Listener {
    void onSupportChange(Support support, ChangeType type);
}
