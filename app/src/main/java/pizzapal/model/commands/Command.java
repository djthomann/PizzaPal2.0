package pizzapal.model.commands;

public interface Command {

    public void execute();

    public void undo();

}
