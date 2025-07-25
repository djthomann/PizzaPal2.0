package pizzapal.model.storage.commands;

public interface Command {

    public void execute();

    public void undo();

}
