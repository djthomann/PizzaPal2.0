package pizzapal.model.commands.add;

import pizzapal.model.domain.entities.Board;

public class AddBoardCommand extends AddCommand {

    private final Board board;

    public AddBoardCommand(Board board) {
        this.board = board;
    }

    @Override
    public void execute() {
        super.execute();
        board.attach();
    }

    @Override
    public void undo() {
        board.delete();
    }

    public Board getBoard() {
        return board;
    }

}
