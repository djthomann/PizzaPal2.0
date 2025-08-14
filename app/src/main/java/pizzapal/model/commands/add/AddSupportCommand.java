package pizzapal.model.commands.add;

import pizzapal.model.domain.core.Storage;
import pizzapal.model.domain.entities.Support;

public class AddSupportCommand extends AddCommand<Support> {

    private final Storage storage;

    public AddSupportCommand(Storage storage, Support support) {
        super(support);
        this.storage = storage;
    }

    @Override
    public void execute() {
        super.execute();
        entity.putInStorage(storage);
    }

    @Override
    public void undo() {
        super.undo();
        entity.delete();
    }

    public Support getSupport() {
        return entity;
    }

}
