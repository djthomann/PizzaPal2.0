package pizzapal.model.commands.delete;

import pizzapal.model.commands.Command;
import pizzapal.model.domain.entities.Entity;
import pizzapal.utils.SoundPlayer;

public abstract class DeleteCommand<E extends Entity> extends Command<E> {

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
