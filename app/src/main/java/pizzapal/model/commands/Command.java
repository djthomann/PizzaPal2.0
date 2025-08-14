package pizzapal.model.commands;

public abstract class Command<E> {

    protected final E entity;

    protected Command(E entity) {
        this.entity = entity;
    }

    public abstract void execute();

    public abstract void undo();

}
