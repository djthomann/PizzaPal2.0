package pizzapal.model.commands.move;

import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;

public class MoveItemCommand extends MoveCommand<Item> {

    private final Board oldBoard, newBoard;

    private final float oldOffset, newOffset;

    public MoveItemCommand(Item item, Board newBoard, float newOffset) {
        super(item);
        oldBoard = item.getBoard();
        this.newBoard = newBoard;
        oldOffset = item.getOffsetX();
        this.newOffset = newOffset;
    }

    @Override
    public void execute() {
        super.execute();
        entity.move(newBoard, newOffset);
    }

    @Override
    public void undo() {
        super.undo();
        entity.move(oldBoard, oldOffset);
    }

}
