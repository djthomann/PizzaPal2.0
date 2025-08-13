package pizzapal.model.listener.change;

import pizzapal.model.domain.entities.Support;
import pizzapal.model.listener.Listener;

public interface SupportChangeListener extends Listener {
    void onSupportChange(Support support, ChangeType type);
}
