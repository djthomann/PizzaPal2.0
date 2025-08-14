package pizzapal.model.commands.delete;

import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;

public class DeleteBoardCommand extends DeleteCommand<Board> {

    private final Support supportLeft;
    private final Support supportRight;

    public DeleteBoardCommand(Board board) {
        super(board);
        this.supportLeft = board.getSupportLeft();
        this.supportRight = board.getSupportRight();
    }

    @Override
    public void execute() {
        super.execute();
        entity.delete();
    }

    @Override
    public void undo() {
        super.undo();
        entity.attach(supportLeft, supportRight);
    }

    public Board getBoard() {
        return entity;
    }

}
