package pizzapal.model.commands.delete;

import pizzapal.model.commands.Command;
import pizzapal.utils.SoundPlayer;

public abstract class DeleteCommand implements Command {

    @Override
    public void execute() {
        SoundPlayer.playGarbageSound();
    }

}
