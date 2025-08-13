package pizzapal.model.observability;

import pizzapal.model.listener.Listener;

public interface Observable<L extends Listener> {

    public void addListener(L l);

    public void removeListener(L l);

    /* TODO */
    // void notifyListeners();

}
