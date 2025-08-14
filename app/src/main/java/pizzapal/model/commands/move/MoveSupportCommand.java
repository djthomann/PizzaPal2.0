package pizzapal.model.commands.move;

import pizzapal.model.domain.entities.Support;

public class MoveSupportCommand extends MoveCommand<Support> {

    private final double oldX, newX;

    public MoveSupportCommand(Support support, double newX, double newY) {
        super(support);
        this.oldX = support.getPosX();
        this.newX = newX;
    }

    @Override
    public void execute() {
        super.execute();
        entity.move((float) newX);
    }

    @Override
    public void undo() {
        super.undo();
        entity.move((float) oldX);
    }

}
