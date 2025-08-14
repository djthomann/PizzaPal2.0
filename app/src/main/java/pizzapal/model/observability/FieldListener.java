package pizzapal.model.observability;

public interface FieldListener<T> {

    void onChange(ObservableField<T> field, T oldValue, T newValue);

}
