package pizzapal.model.commands.move;

import pizzapal.model.commands.EntityCommand;
import pizzapal.model.domain.entities.Entity;
import pizzapal.utils.SoundPlayer;

public abstract class MoveCommand<E extends Entity> extends EntityCommand<E> {

    protected MoveCommand(E entity) {
        super(entity);
    }

    @Override
    public void execute() {
        SoundPlayer.playMoveSound();
    }

    @Override
    public void undo() {
        SoundPlayer.playMoveSound();
    }

}
