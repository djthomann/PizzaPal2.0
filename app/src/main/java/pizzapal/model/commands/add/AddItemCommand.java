package pizzapal.model.commands.add;

import pizzapal.model.domain.entities.Board;
import pizzapal.model.domain.entities.Item;

public class AddItemCommand extends AddCommand {

    private final Item item;

    private final Board board;

    public AddItemCommand(Item item, Board board) {
        this.item = item;
        this.board = board;
    }

    @Override
    public void execute() {
        super.execute();
        item.placeOn(board);
    }

    @Override
    public void undo() {
        item.delete();
    }

    // TODO: unnecessary???
    public Item getItem() {
        return item;
    }

}
