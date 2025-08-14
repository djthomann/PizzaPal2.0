package pizzapal.model.commands.add;

import pizzapal.model.commands.Command;
import pizzapal.model.domain.entities.Entity;
import pizzapal.utils.SoundPlayer;

public abstract class AddCommand<E extends Entity> implements Command {

    protected final E entity;

    protected AddCommand(E entity) {
        this.entity = entity;
    }

    @Override
    public void execute() {
        SoundPlayer.playAddSound();
    }

    @Override
    public void undo() {
        SoundPlayer.playGarbageSound();
    }

}
