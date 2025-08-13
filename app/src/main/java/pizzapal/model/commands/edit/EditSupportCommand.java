package pizzapal.model.commands.edit;

import javafx.scene.paint.Color;
import pizzapal.model.domain.entities.SerializableColor;
import pizzapal.model.domain.entities.Support;

public class EditSupportCommand extends EditCommand {

    private final Support support;

    private final float oldWidth;
    private final float newWidth;

    private final float oldHeight;
    private final float newHeight;

    private final SerializableColor oldColor;
    private final SerializableColor newColor;

    public EditSupportCommand(Support support, float newWidth, float newHeight,
            Color newColor) {
        this.support = support;
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
        support.edit(newWidth, newHeight, newColor);
    }

    @Override
    public void undo() {
        support.edit(oldWidth, oldHeight, oldColor);
    }

}
