package pizzapal.model.storage.commands;

import pizzapal.model.entities.Support;

public class MoveSupportCommand implements Command {

    private final Support support;
    private final double oldX, oldY;
    private final double newX, newY;

    public MoveSupportCommand(Support support, double newX, double newY) {
        this.support = support;
        this.oldX = support.getPositionX();
        this.oldY = support.getPositionY();
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void execute() {
        support.move((float) newX);
    }

    @Override
    public void undo() {
        support.move((float) oldX);
    }

}
