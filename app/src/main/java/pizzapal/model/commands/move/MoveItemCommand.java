package pizzapal.model.commands.move;

import pizzapal.model.commands.Command;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;

public class MoveItemCommand implements Command {

    private final Item item;

    private final Board oldBoard;
    private final Board newBoard;

    private final float oldOffset;
    private final float newOffset;

    public MoveItemCommand(Item item, Board newBoard, float newOffset) {
        this.item = item;
        oldBoard = item.getBoard();
        this.newBoard = newBoard;
        oldOffset = item.getOffsetX();
        this.newOffset = newOffset;
    }

    @Override
    public void execute() {
        item.move(newBoard, newOffset);
    }

    @Override
    public void undo() {
        item.move(oldBoard, oldOffset);
    }

}
