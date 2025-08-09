package pizzapal.model.commands.add;

import pizzapal.model.commands.Command;
import pizzapal.utils.SoundPlayer;

public abstract class AddCommand implements Command {

    @Override
    public void execute() {
        SoundPlayer.playAddSound();
    }

}
