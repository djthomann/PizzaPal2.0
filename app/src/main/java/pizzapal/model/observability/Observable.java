package pizzapal.model.observability;

public interface Observable<L extends Listener> {

    public void addListener(L l);

    public void removeListener(L l);

    /* TODO */
    // void notifyListeners();

}
