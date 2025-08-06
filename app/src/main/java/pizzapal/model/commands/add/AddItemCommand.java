package pizzapal.model.commands.add;

import pizzapal.model.commands.Command;
import pizzapal.model.domain.entities.Item;

public class AddItemCommand implements Command {

    private final Item item;

    public AddItemCommand(Item item) {
        this.item = item;
    }

    @Override
    public void execute() {
        item.place();
    }

    @Override
    public void undo() {
        item.delete();
    }

    public Item getItem() {
        return item;
    }

}
