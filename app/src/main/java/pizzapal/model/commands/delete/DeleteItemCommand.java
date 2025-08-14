package pizzapal.model.commands.delete;

import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;

public class DeleteItemCommand extends DeleteCommand<Item> {

    private final Board board;

    public DeleteItemCommand(Item item) {
        super(item);
        this.board = item.getBoard();
    }

    @Override
    public void execute() {
        super.execute();
        entity.delete();
    }

    @Override
    public void undo() {
        super.undo();
        entity.placeOn(board);
    }

    public Item getItem() {
        return entity;
    }

}
