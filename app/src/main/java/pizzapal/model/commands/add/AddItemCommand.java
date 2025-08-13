package pizzapal.model.commands.add;

import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;

public class AddItemCommand extends AddCommand<Item> {

    private final Board board;

    public AddItemCommand(Item item, Board board) {
        super(item);
        this.board = board;
    }

    @Override
    public void execute() {
        super.execute();
        entity.placeOn(board);
    }

    @Override
    public void undo() {
        entity.delete();
    }

    public Item getItem() {
        return entity;
    }

}
