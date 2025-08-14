package pizzapal.model.commands.edit;

import javafx.scene.paint.Color;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.SerializableColor;

public class EditItemCommand extends EditCommand<Item> {

    private final float newWidth, oldWidth;
    private final float newHeight, oldHeight;

    private final SerializableColor newColor, oldColor;

    public EditItemCommand(Item item, float newWidth, float newHeight, Color newColor) {
        super(item);
        oldWidth = item.getWidth();
        this.newWidth = newWidth;
        oldHeight = item.getHeight();
        this.newHeight = newHeight;
        oldColor = item.getColor();
        this.newColor = new SerializableColor(newColor);
    }

    @Override
    public void execute() {
        super.execute();
        entity.edit(newWidth, newHeight, newColor);
    }

    @Override
    public void undo() {
        super.undo();
        entity.edit(oldWidth, oldHeight, oldColor);
    }

}
