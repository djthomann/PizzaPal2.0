package pizzapal.model.commands.edit;

import javafx.scene.paint.Color;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.SerializableColor;

public class EditBoardCommand extends EditCommand {

    private final Board board;

    private final float oldHeight;
    private final float newHeight;

    private final SerializableColor oldColor;
    private final SerializableColor newColor;

    public EditBoardCommand(Board board, float newHeight, Color newColor) {
        this.board = board;
        oldHeight = board.getHeight();
        this.newHeight = newHeight;
        oldColor = board.getColor();
        this.newColor = new SerializableColor(newColor);
    }

    @Override
    public void execute() {
        super.execute();
        board.edit(newHeight, newColor);
    }

    @Override
    public void undo() {
        board.edit(oldHeight, oldColor);
    }

}
