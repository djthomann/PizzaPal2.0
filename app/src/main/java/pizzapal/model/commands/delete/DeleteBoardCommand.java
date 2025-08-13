package pizzapal.model.commands.delete;

import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;

public class DeleteBoardCommand extends DeleteCommand {

    private final Board board;
    private final Support supportLeft;
    private final Support supportRight;

    public DeleteBoardCommand(Board board) {
        this.board = board;
        this.supportLeft = board.getSupportLeft();
        this.supportRight = board.getSupportRight();
    }

    @Override
    public void execute() {
        super.execute();
        board.delete();
    }

    @Override
    public void undo() {
        board.attach(supportLeft, supportRight);
    }

    public Board getBoard() {
        return board;
    }

}
