package pizzapal.model.commands.delete;

import pizzapal.model.commands.Command;
import pizzapal.model.domain.entities.Entity;
import pizzapal.utils.SoundPlayer;

public abstract class DeleteCommand<E extends Entity> implements Command {

    protected final E entity;

    protected DeleteCommand(E entity) {
        this.entity = entity;
    }

    @Override
    public void execute() {
        SoundPlayer.playGarbageSound();
    }

}
