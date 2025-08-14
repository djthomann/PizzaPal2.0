package pizzapal.model.commands.delete;

import java.util.ArrayList;
import java.util.List;

import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;
import pizzapal.model.domain.entities.Support;

public class DeleteBoardCommand extends DeleteCommand<Board> {

    private final Support supportLeft;
    private final Support supportRight;

    private final List<Item> items;

    public DeleteBoardCommand(Board board) {
        super(board);
        this.supportLeft = board.getSupportLeft();
        this.supportRight = board.getSupportRight();
        items = new ArrayList<>(board.getItems());
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
        for (Item i : items) {
            entity.addItem(i);
        }
    }

    public Board getBoard() {
        return entity;
    }

}
