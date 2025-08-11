package pizzapal.model.commands.add;

import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;

public class AddBoardCommand extends AddCommand {

    private final Board board;

    private final Support supportLeft;
    private final Support supportRight;

    public AddBoardCommand(Board board, Support supportLeft, Support supportRight) {
        this.board = board;
        this.supportLeft = supportLeft;
        this.supportRight = supportRight;
    }

    @Override
    public void execute() {
        super.execute();
        board.attach(supportLeft, supportRight);
    }

    @Override
    public void undo() {
        // TODO
        board.delete();
    }

    public Board getBoard() {
        return board;
    }

}
