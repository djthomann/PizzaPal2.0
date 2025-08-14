package pizzapal.model.commands.edit;

import javafx.scene.paint.Color;
import pizzapal.model.domain.entities.SerializableColor;
import pizzapal.model.domain.entities.Support;

public class EditSupportCommand extends EditCommand<Support> {

    private final float newWidth, oldWidth;
    private final float newHeight, oldHeight;

    private final SerializableColor newColor, oldColor;

    public EditSupportCommand(Support support, float newWidth, float newHeight,
            Color newColor) {
        super(support);
        this.oldWidth = support.getWidth();
        this.newWidth = newWidth;
        this.oldHeight = support.getHeight();
        this.newHeight = newHeight;
        this.oldColor = support.getColor();
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
