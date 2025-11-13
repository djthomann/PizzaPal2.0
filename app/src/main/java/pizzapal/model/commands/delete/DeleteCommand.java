package pizzapal.model.commands.delete;

import pizzapal.model.commands.EntityCommand;
import pizzapal.model.domain.entities.Entity;
import pizzapal.utils.SoundPlayer;

public abstract class DeleteCommand<E extends Entity> extends EntityCommand<E> {

    protected DeleteCommand(E entity) {
        super(entity);
    }

    @Override
    public void execute() {
        SoundPlayer.playGarbageSound();
    }

    @Override
    public void undo() {
        SoundPlayer.playAddSound();
    }

}
