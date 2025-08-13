package pizzapal.model.commands.delete;

import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;

public class DeleteItemCommand extends DeleteCommand {

    private final Item item;
    private final Board board;

    public DeleteItemCommand(Item item) {
        this.item = item;
        this.board = item.getBoard();
    }

    @Override
    public void execute() {
        super.execute();
        item.delete();
    }

    @Override
    public void undo() {
        item.placeOn(board);
    }

    public Item getItem() {
        return item;
    }

}
