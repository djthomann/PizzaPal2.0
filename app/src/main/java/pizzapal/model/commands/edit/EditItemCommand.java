package pizzapal.model.commands.edit;

import javafx.scene.paint.Color;
import pizzapal.model.commands.Command;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.SerializableColor;
import pizzapal.utils.SoundPlayer;

public class EditItemCommand implements Command {

    private final Item item;

    private final float oldWidth;
    private final float newWidth;

    private final float oldHeight;
    private final float newHeight;

    private final SerializableColor oldColor;
    private final SerializableColor newColor;

    public EditItemCommand(Item item, float newWidth, float newHeight, Color newColor) {
        this.item = item;
        oldWidth = item.getWidth();
        this.newWidth = newWidth;
        oldHeight = item.getHeight();
        this.newHeight = newHeight;
        oldColor = item.getColor();
        this.newColor = new SerializableColor(newColor);
    }

    @Override
    public void execute() {
        SoundPlayer.playEditSound();
        item.edit(newWidth, newHeight, newColor);
    }

    @Override
    public void undo() {
        item.edit(oldWidth, oldHeight, oldColor);
    }

}
