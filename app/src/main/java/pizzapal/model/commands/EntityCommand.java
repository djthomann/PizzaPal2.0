package pizzapal.model.commands;

public abstract class EntityCommand<E> {

    protected final E entity;

    protected EntityCommand(E entity) {
        this.entity = entity;
    }

    public abstract void execute();

    public abstract void undo();

}
