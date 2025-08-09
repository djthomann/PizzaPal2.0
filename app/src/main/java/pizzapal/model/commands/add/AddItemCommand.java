package pizzapal.model.commands.add;

import pizzapal.model.domain.entities.Item;

public class AddItemCommand extends AddCommand {

    private final Item item;

    public AddItemCommand(Item item) {
        this.item = item;
    }

    @Override
    public void execute() {
        super.execute();
        item.place();
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
