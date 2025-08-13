package pizzapal.model.commands.add;

import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Support;

public class AddBoardCommand extends AddCommand<Board> {

    private final Support supportLeft;
    private final Support supportRight;

    public AddBoardCommand(Board board, Support supportLeft, Support supportRight) {
        super(board);
        this.supportLeft = supportLeft;
        this.supportRight = supportRight;
    }

    @Override
    public void execute() {
        super.execute();
        entity.attach(supportLeft, supportRight);
    }

    @Override
    public void undo() {
        entity.delete();
    }

    public Board getBoard() {
        return entity;
    }

}
