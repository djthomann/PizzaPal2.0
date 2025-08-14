package pizzapal.model.commands.edit;

import javafx.scene.paint.Color;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.SerializableColor;

public class EditBoardCommand extends EditCommand<Board> {

    private final float newHeight, oldHeight;

    private final SerializableColor newColor, oldColor;

    public EditBoardCommand(Board board, float newHeight, Color newColor) {
        super(board);
        oldHeight = board.getHeight();
        this.newHeight = newHeight;
        oldColor = board.getColor();
        this.newColor = new SerializableColor(newColor);
    }

    @Override
    public void execute() {
        super.execute();
        entity.edit(newHeight, newColor);
    }

    @Override
    public void undo() {
        super.undo();
        entity.edit(oldHeight, oldColor);
    }

}
