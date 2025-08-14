package pizzapal.model.commands.move;

import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;

public class MoveBoardCommand extends MoveCommand<Board> {

    private final Support newSupportLeft, oldSupportLeft;
    private final Support newSupportRight, oldSupportRight;

    private final float newOffset, oldOffset;

    public MoveBoardCommand(Board board, Support newSupportLeft, Support newSupportRight, float newY) {
        super(board);
        this.newSupportLeft = newSupportLeft;
        this.oldSupportLeft = board.getSupportLeft();
        this.newSupportRight = newSupportRight;
        this.oldSupportRight = board.getSupportRight();
        this.newOffset = newY;
        this.oldOffset = board.getOffsetY();
    }

    @Override
    public void execute() {
        super.execute();
        entity.move(newSupportLeft, newSupportRight, newOffset);
    }

    @Override
    public void undo() {
        super.undo();
        entity.move(oldSupportLeft, oldSupportRight, oldOffset);
    }

}
