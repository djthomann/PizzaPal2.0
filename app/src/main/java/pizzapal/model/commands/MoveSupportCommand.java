package pizzapal.model.commands;

import pizzapal.model.domain.entities.Support;

public class MoveSupportCommand implements Command {

    private final Support support;
    private final double oldX, oldY;
    private final double newX, newY;

    public MoveSupportCommand(Support support, double newX, double newY) {
        this.support = support;
        this.oldX = support.getPosX();
        this.oldY = support.getPosY();
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
