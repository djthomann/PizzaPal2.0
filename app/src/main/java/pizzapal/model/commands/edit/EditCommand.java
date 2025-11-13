package pizzapal.model.commands.edit;

import pizzapal.model.commands.EntityCommand;
import pizzapal.model.domain.entities.Entity;
import pizzapal.utils.SoundPlayer;

public abstract class EditCommand<E extends Entity> extends EntityCommand<E> {

    protected EditCommand(E entity) {
        super(entity);
    }

    @Override
    public void execute() {
        SoundPlayer.playEditSound();
    }

    @Override
    public void undo() {
        SoundPlayer.playEditSound();
    }

}
