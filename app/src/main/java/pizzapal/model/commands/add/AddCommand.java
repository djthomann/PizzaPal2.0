package pizzapal.model.commands.add;

import pizzapal.model.commands.Command;
import pizzapal.model.domain.entities.Entity;
import pizzapal.utils.SoundPlayer;

public abstract class AddCommand<E extends Entity> extends Command<E> {

    protected AddCommand(E entity) {
        super(entity);
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
