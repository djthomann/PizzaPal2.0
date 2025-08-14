package pizzapal.model.observability;

import java.util.ArrayList;
import java.util.List;

public class ObservableField<T> {

    private T value;

    private List<FieldListener<T>> listeners;

    public ObservableField(T value) {
        this.value = value;
        listeners = new ArrayList<>();
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        T old = this.value;
        this.value = value;
        notifyListeners(old);
    }

    private void notifyListeners(T oldValue) {
        for (FieldListener<T> l : listeners) {
            l.onChange(this, oldValue, value);
        }
    }

    public void addListener(FieldListener<T> l) {
        listeners.add(l);
    }

    public void removeListener(FieldListener<T> l) {
        listeners.remove(l);
    }

}
