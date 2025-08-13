package pizzapal.model.commands.edit;

import pizzapal.model.commands.Command;
import pizzapal.utils.SoundPlayer;

public abstract class EditCommand implements Command {

    @Override
    public void execute() {
        SoundPlayer.playEditSound();
    }

}
