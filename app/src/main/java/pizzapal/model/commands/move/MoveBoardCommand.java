package pizzapal.model.commands.move;

import pizzapal.model.commands.Command;
import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;

public class MoveBoardCommand implements Command {

    private final Board board;

    private final Support newSupportLeft;
    private final Support oldSupportLeft;

    private final Support newSupportRight;
    private final Support oldSupportRight;

    private final float newOffset;
    private final float oldOffset;

    public MoveBoardCommand(Board board, Support newSupportLeft, Support newSupportRight, float newY) {
        this.board = board;
        this.newSupportLeft = newSupportLeft;
        this.oldSupportLeft = board.getSupportLeft();
        this.newSupportRight = newSupportRight;
        this.oldSupportRight = board.getSupportRight();
        this.newOffset = newY;
        this.oldOffset = board.getOffsetY();
    }

    @Override
    public void execute() {
        board.move(newSupportLeft, newSupportRight, newOffset);
    }

    @Override
    public void undo() {
        board.move(oldSupportLeft, oldSupportRight, oldOffset);
    }

}
